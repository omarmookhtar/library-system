# مازن — Backend: Book Catalog Service

## دورك
سيرفس الكتب. هو أبسط سيرفس، وبقية السيرفسز بتعتمد عليه، فسرعتك فيه مهمة. خد وقتك وركّز على الجودة.

## فولدرك
`catalog-service/` (انسخ `service-template`) — Port **8082**، DB `catalog_db`

## اللي بتستلمه من عمر
- `service-template/` و `CONTRACT.md` (BR-8, BR-9, BR-10)

## اللي هتكتبه
- Entity `Book` (id, isbn [unique], title, author, category, totalCopies, copiesAvailable, price)
- Repository (`findByTitleContainingIgnoreCase`, `existsByIsbn`, …)
- DTOs: `BookRequest` (مع `@NotBlank` و`@Min`)، `BookResponse`
- Service + Controller:
  - CRUD من الـ CONTRACT (بحث بـ title/author/category)
  - `POST /internal/books/{id}/decrease` و `/increase` (لـ Borrowing)
- القواعد: ISBN فريد (409)، منع الحذف لو فيه نسخ مستعارة، `copiesAvailable` بين 0 و`totalCopies`

## لازم تتعلمه
- Spring Data JPA: الـ derived queries و`@Query`
- Bean Validation: `@NotBlank`, `@Min`, `@Size`, `@Valid`
- الفرق بين Entity وDTO وإزاي تحول بينهم (mapping يدوي بسيط)
- HTTP status codes (201, 204, 404, 409)

## ترتيب الشغل
1. الأسبوع الأول: Entity + CRUD شغال + Swagger
2. بعدها: البحث والـ rules
3. بعدها: endpoints الـ internal
> لو واقف في أي حاجة أكتر من ساعتين اسأل عمر أو حسن/عماد، ده طبيعي.

## الدوكيومنتس
- **ERD لكل النظام** (draw.io): كل واحد من الباك يبعتلك جداوله (أعمدة + مفاتيح) وأنت ترسم وتحط الـ cardinality
- **OCL (1):**
```
context Book
inv validCopies: self.copiesAvailable >= 0 and self.copiesAvailable <= self.totalCopies
```
والمكان: Service check + `@Min(0)` + CHECK constraint في DB.

## نصيحة
طبّق نفس الأسلوب (Controller → Service → Repository + DTO) بالظبط زي ما في القالب. هو نفس الشكل اللي الباقيين هيمشوا عليه، وده بيسهّل الربط كتير.
