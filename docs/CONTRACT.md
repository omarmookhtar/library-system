# CONTRACT — الاتفاق الرسمي بين الفريق

> ممنوع حد يغير الملف ده من غير ما يقول لعمر. أي تغيير = إعلان على الجروب.

## 1) السيرفسز والبورتات والداتابيز

| Service | اسم Eureka | Port | Database | المسؤول |
|---|---|---|---|---|
| Eureka Server | eureka-server | 8761 | — | عمر |
| API Gateway | api-gateway | 8080 | — | عمر |
| User | user-service | 8081 | user_db | عمر |
| Book Catalog | catalog-service | 8082 | catalog_db | مازن |
| Borrowing | borrowing-service | 8083 | borrowing_db | حسن |
| Fines/Reports | fines-service | 8084 | fines_db | عماد |

## 2) الـ Roles والـ JWT

- الـ Roles بالظبط: `ADMIN`, `LIBRARIAN`, `MEMBER`
- الـ JWT بيطلع من user-service عند الـ login، وبيحتوي: `sub` (userId)، `role`، `exp`
- الـ Gateway بيتحقق من التوكن ومن صلاحية الـ role لكل endpoint، وبعدين بيبعت لكل سيرفس:
  - `X-User-Id`
  - `X-User-Role`
- السيرفسز بتقرا الهيدرز دي عن طريق `UserContext` (موجود في القالب). **مفيش حد بيعمل JWT parsing في سيرفسه.**
- أي endpoint بيبدأ بـ `/internal/**` ده للتواصل بين السيرفسز بس (Feign) والـ Gateway **مش بيعمله route**.

## 3) شكل الـ Error الموحد

```json
{ "status": 400, "message": "Validation failed", "errors": { "title": "must not be blank" }, "timestamp": "..." }
```
موجود جاهز في القالب (`GlobalExceptionHandler`). استخدم `ApiException.conflict("...")` و`notFound` و`badRequest` و`forbidden`.

## 4) قواعد الـ REST

- أسماء جمع: `/api/books`، `/api/borrowings/{id}`
- النجاح: `200` (GET/PUT/PATCH)، `201` (POST إنشاء)، `204` (DELETE)
- الأخطاء: `400` بيانات غلط، `401` من غير توكن، `403` role غلط، `404` مش موجود، `409` تعارض/خرق قاعدة
- كل request/response عن طريق **DTO**، ممنوع نرجّع الـ Entity
- الأسماء: `XxxRequest` للدخل، `XxxResponse` للخرج
- التواريخ: ISO (`2026-11-20`)، الفلوس: `BigDecimal` بالجنيه

## 5) الـ Endpoints (المسودة الأولية)

### user-service
| Method | Path | الـ Role |
|---|---|---|
| POST | /api/auth/register | public (بيعمل MEMBER) |
| POST | /api/auth/login | public |
| GET | /api/users/me | أي مستخدم |
| GET | /api/users | ADMIN, LIBRARIAN |
| GET | /api/users/{id} | ADMIN, LIBRARIAN |
| PATCH | /api/users/{id}/role | ADMIN |
| DELETE | /api/users/{id} | ADMIN |
| GET | /internal/users/{id} | داخلي (Feign) |

### catalog-service
| Method | Path | الـ Role |
|---|---|---|
| GET | /api/books?title=&author=&category= | أي مستخدم |
| GET | /api/books/{id} | أي مستخدم |
| POST | /api/books | ADMIN, LIBRARIAN |
| PUT | /api/books/{id} | ADMIN, LIBRARIAN |
| DELETE | /api/books/{id} | ADMIN |
| GET | /internal/books/{id} | داخلي |
| POST | /internal/books/{id}/decrease | داخلي (ينقص copiesAvailable) |
| POST | /internal/books/{id}/increase | داخلي |

### borrowing-service
| Method | Path | الـ Role |
|---|---|---|
| POST | /api/borrowings | MEMBER, LIBRARIAN |
| POST | /api/borrowings/{id}/return | LIBRARIAN, ADMIN |
| PATCH | /api/borrowings/{id}/renew | MEMBER |
| GET | /api/borrowings/my | MEMBER |
| GET | /api/borrowings | ADMIN, LIBRARIAN |
| GET | /api/borrowings/{id} | ADMIN, LIBRARIAN |
| GET | /internal/borrowings/overdue | داخلي |

### fines-service
| Method | Path | الـ Role |
|---|---|---|
| GET | /api/fines/my | MEMBER |
| GET | /api/fines?status=UNPAID | ADMIN, LIBRARIAN |
| POST | /api/fines/{id}/pay | LIBRARIAN, ADMIN |
| GET | /api/reports/overdue | ADMIN, LIBRARIAN |
| GET | /api/reports/popular-books | ADMIN |
| GET | /api/reports/summary | ADMIN |
| POST | /internal/fines/calculate | داخلي (من Borrowing عند الإرجاع) |
| GET | /internal/fines/member/{memberId}/unpaid-total | داخلي |

## 6) الـ Business Rules (متفق عليها)

**Borrowing**
- BR-1: العضو يستعير 3 كتب نشطة كحد أقصى.
- BR-2: مفيش استعارة لو الكتاب `copiesAvailable = 0`.
- BR-3: مفيش استعارة لو على العضو غرامات غير مدفوعة.
- BR-4: مدة الاستعارة 14 يوم، والتجديد مرة واحدة (+7 أيام) وبشرط إنه مش متأخر.
- BR-5: مينفعش نفس العضو يستعير نفس الكتاب مرتين وهو لسه مش مرجعه.

**Fines**
- BR-6: الغرامة 5 جنيه عن كل يوم تأخير بعد الـ due date، وتتحسب عند الإرجاع.
- BR-7: مينفعش تدفع غرامة مدفوعة بالفعل (409).

**Catalog**
- BR-8: الـ ISBN فريد (409 لو متكرر).
- BR-9: مينفعش تمسح كتاب فيه نسخ مستعارة.
- BR-10: `copiesAvailable` ما تقلش عن 0 ولا تزيد عن `totalCopies`.

**User**
- BR-11: الإيميل فريد، والباسورد BCrypt بحد أدنى 8 حروف.
- BR-12: الأدمن مينفعش يمسح نفسه أو يغير role نفسه.

## 7) مين بيكلم مين (Feign بالاسم من Eureka)

```
Borrowing → user-service     (التأكد إن العضو موجود)
Borrowing → catalog-service  (التأكد من التوافر + نقص/زيادة النسخ)
Borrowing → fines-service    (فحص الغرامات غير المدفوعة + حساب الغرامة عند الإرجاع)
Fines     → borrowing-service (تقرير المتأخرات)
```
