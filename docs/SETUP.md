# تجهيز البيئة (Setup) — كل الفريق

## 1) البرامج المطلوبة (كل واحد يثبتها)

| البرنامج | النسخة | ليه |
|---|---|---|
| JDK | 17 (Temurin) | تشغيل Spring Boot 3 |
| Maven | 3.9+ (أو اللي جوه IntelliJ) | بناء المشروع |
| IntelliJ IDEA (Community تكفي) | أي نسخة حديثة | الـ IDE |
| Docker Desktop | آخر نسخة | `docker compose up` |
| Git | آخر نسخة | الـ GitHub |
| Postman | آخر نسخة | تجربة الـ APIs |
| Node.js LTS | للفرونت بس | React/Vite |

تأكد: `java -version` يطلع 17، و `docker --version` يشتغل.

## 2) نفس النسخ عند الكل (مهم جدًا)

- Java **17**
- Spring Boot **3.3.5**
- Spring Cloud **2023.0.3**
- springdoc-openapi **2.6.0**
- PostgreSQL **16** (من Docker، مش لازم تثبته)

ممنوع حد يغير النسخ لوحده. ده اللي بيمنع مشاكل "على جهازي شغال".

## 3) الـ Git

```
main              ← مفيش حد يكتب عليه مباشرة (عمر بس بيعمل merge)
feature/<اسمك>-<الحاجة>   ← مثال: feature/hassan-borrow-rules
```

1. `git clone <repo>`
2. `git checkout -b feature/<اسمك>-<الحاجة>`
3. اشتغل **جوه فولدر سيرفسك بس**
4. `git add . && git commit -m "borrowing: add borrow endpoint"`
5. `git push origin feature/...` ثم Pull Request لـ main
6. عمر يعمل Merge

كل واحد لازم يعمل commits باسمه (الدكتورة هتشوف).

## 4) إزاي تشتغل على سيرفسك (من غير ما تبوظ حاجة)

1. انسخ فولدر `service-template` وغيّر اسمه (مثلًا `catalog-service`).
2. غيّر 4 حاجات (من `CONTRACT.md`):
   - `artifactId` في `pom.xml`
   - `server.port` و `spring.application.name` في `application.yml`
   - اسم الداتابيز في `DB_URL` الافتراضي
   - اسم الـ package (Refactor → Rename في IntelliJ)
3. شغّل الداتابيز: `docker compose up -d postgres`
4. شغّل Eureka من IntelliJ (`eureka-server`) ثم سيرفسك.
5. افتح `http://localhost:8761` وتأكد إن سيرفسك ظهر.
6. Swagger: `http://localhost:<port>/swagger-ui.html`

## 5) تشغيل النظام كله (Docker)

```
docker compose up --build
```

- Eureka: http://localhost:8761
- Gateway: http://localhost:8080
- الفرونت وPostman بيكلموا **8080 بس**.

لو سيرفس لسه مش جاهز، علّم عليه في `docker-compose.yml` بـ `#`.

## 6) أخطاء شائعة

| المشكلة | الحل |
|---|---|
| السيرفس مش بيظهر في Eureka | تأكد `spring.application.name` والـ `EUREKA_URL` |
| `Connection refused` للداتابيز | شغّل `docker compose up -d postgres` وتأكد من اسم الـ DB |
| Port already in use | غيّر البورت أو اقفل البرنامج اللي شاغله |
| Gateway يرجّع 503 | السيرفس مش مسجل في Eureka لسه (استنى 30 ثانية) |
| اختلاف نسخ Spring | استخدم النسخ في القسم 2 بالظبط |
