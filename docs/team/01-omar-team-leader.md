# عمر — Team Leader / Backend

## دورك
أنت مسؤول عن **الأساس والربط**، يعني باقي الفريق بياخد منك الجاهز.

## ملفاتك (فولدرات)
- `eureka-server/` ← جاهز في الـ kit
- `api-gateway/` ← JWT filter + route + مصفوفة الصلاحيات
- `user-service/` ← register/login/BCrypt/JWT generation
- `docker-compose.yml` + `docker/init-db.sql`
- `docs/CONTRACT.md` ← أنت صاحبه
- `README.md` ← بورتات + لينكات Eureka/Swagger + طريقة التشغيل

## اللي بتديه للفريق (copy-paste)
| لمين | تديه إيه |
|---|---|
| الباك الـ 3 | `service-template/` + `CONTRACT.md` + `SETUP.md` + ملفه |
| علي (فرونت) | `CONTRACT.md` + رابط الـ Gateway + قايمة الـ roles + شكل الـ login response |
| الكل | Aspect جاهز (من عماد) تنسخه في كل سيرفس |

## اللي لازم تتعلمه / تراجع عليه
- Spring Cloud Gateway: routes بـ `lb://`، global filter، تمرير الهيدرز
- Spring Security + BCrypt، توليد وفحص JWT (مكتبة jjwt)
- مصفوفة الصلاحيات في الـ Gateway (WebFlux Security: `authorizeExchange`)
- Eureka + Docker networking (أسماء الـ containers)
- (للمناقشة) هتتسأل: ليه الـ JWT في الـ Gateway؟ إيه الفرق بين `lb://` وبورت ثابت؟

## الدوكيومنتس
- Architecture diagram (السيرفسز + Gateway + Eureka + DBs + Docker + البورتات)
- SRS: القسم 1 (Introduction) والقسم 2 (Users and roles) + تجميع الـ SRS النهائي
- الـ README والتقرير النهائي (تجمع أجزاء الفريق)
- OCL: 2 على User

## الخطة
1. **أسبوع 1:** Eureka + docker-compose + Gateway (route + JWT) + user-service (register/login) + تسليم القالب للفريق
2. **أسبوع 2:** مراجعة PRs يوميًا + Postman تجريبي للـ login
3. **أسبوع 3:** الربط + اختبار الـ flow كامل (login → استعارة → إرجاع → غرامة)
4. **أسبوع 4:** Docker نهائي + التقرير + بروفة المناقشة

## نصيحة
اعمل ملف على الجروب اسمه "Daily status" (كل واحد يكتب سطرين: عملت إيه، واقف عند إيه). ده بيوفر عليك تلف على كل واحد.
