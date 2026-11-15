# سيف — Frontend: Fines/Reports + Testing

## دورك
صفحات الغرامات والتقارير + **اختبار النظام بالكامل بـ Postman**.

## اللي بتستلمه
- من علي: مشروع الأساس
- من عمر: `CONTRACT.md`

## اللي هتكتبه
**الصفحات:**
- "غراماتي" (Member): `/api/fines/my`
- إدارة الغرامات (Librarian): قايمة + فلتر UNPAID + زرار "تم الدفع"
- Dashboard للأدمن: `/api/reports/summary`, `/overdue`, `/popular-books` (جداول بسيطة أو chart)

**Postman collection (الأهم):**
- Collection واحدة، مقسمة فولدرات **حسب السيرفس**
- Variables: `{{baseUrl}}` = `http://localhost:8080` و `{{token}}`
- Script في request الـ login يحفظ التوكن تلقائيًا:
  `pm.collectionVariables.set("token", pm.response.json().token);`
- لكل endpoint: مثال **valid** ومثال **invalid** (400/403/404/409)
- صدّرها JSON وحطها في `docs/`

## لازم تتعلمه
- Postman: variables, tests scripts, collection export
- React (زي يحيى)، وعرض الجداول والـ charts البسيطة (ممكن تستخدم recharts)
- فهم الـ status codes عشان تعرف تكتب الـ invalid cases صح

## الدوكيومنتس
- **Postman collection** (ملف JSON في `docs/`)
- **SRS:** Functional Requirements لـ Borrowing و Fines + **Non-Functional Requirements** (قابلة للقياس)
  - مثال: "Login responds in less than 2 seconds" و"Passwords are stored hashed (BCrypt)"
- وصف use cases الاستعارة والإرجاع

## نصيحة
بعد ما أي باك يخلّص endpoint، جرّبه فورًا في Postman وبلّغه لو فيه مشكلة. أنت اللي هتكتشف المشاكل بدري قبل المناقشة.
