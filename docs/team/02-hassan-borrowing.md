# حسن — Backend: Borrowing Service

## دورك
أصعب سيرفس في المشروع لأنه بيكلم 3 سيرفسز تانية. ده هو "قلب" النظام وأنت مناسب ليه.

## فولدرك
`borrowing-service/` (انسخ `service-template`) — Port **8083**، DB `borrowing_db`

## اللي بتستلمه من عمر
- `service-template/` و `CONTRACT.md` (الـ endpoints والـ rules BR-1..BR-5)
- شكل الـ DTOs الخاصة بـ User/Book/Fine اللي هتحتاجها في الـ Feign clients

## اللي هتكتبه
- Entity: `Borrowing` (id, memberId, bookId, borrowDate, dueDate, returnDate, status[ACTIVE/RETURNED/OVERDUE], renewed)
- Repository, Service, Controller, DTOs
- 3 Feign clients: `UserClient`, `CatalogClient`, `FinesClient` (بالاسم: `@FeignClient(name = "catalog-service")`)
- القواعد BR-1 إلى BR-5 في الـ Service
- Endpoints: borrow, return, renew, my, list, get (من الـ CONTRACT)
- `GET /internal/borrowings/overdue` لعماد

## لازم تتعلمه
- OpenFeign مع Eureka (`@EnableFeignClients` موجودة في القالب)
- `@Transactional` ومعنى الـ rollback لو Feign فشل في النص
- إزاي تترجم أخطاء السيرفس التاني (الـ `GlobalExceptionHandler` بيعمل ده)
- JPA queries: `countByMemberIdAndStatus`, `existsByMemberIdAndBookIdAndStatus`
- (مناقشة) إيه اللي بيحصل لو catalog-service واقعة؟

## ترتيب الشغل
1. الـ Entity + CRUD بدون Feign (يومين)
2. BR-1, BR-4, BR-5 (قواعد محلية)
3. Feign لـ catalog (BR-2 + decrease/increase)
4. Feign لـ user و fines (BR-3 + حساب الغرامة عند الإرجاع)

## الدوكيومنتس
- **Sequence diagram:** عملية الاستعارة (Client → Gateway → Borrowing → Catalog/User/Fines → DB)
- **Activity diagram:** عملية الاستعارة بالقرارات (متاح؟ عليه غرامة؟ وصل للحد؟)
- **OCL (2):**
```
context Borrowing
inv validDates: self.dueDate > self.borrowDate

context BorrowingService::borrow(memberId : Long, bookId : Long)
pre limitNotReached: Borrowing.allInstances()->select(b | b.memberId = memberId and b.status = 'ACTIVE')->size() < 3
```
وقول فين متطبقين (service check / validation).

## نصيحة
ابدأ بشغل بدون Feign وبـ mock، وبعد ما سيرفس الكتالوج وUser يبقوا جاهزين وصّلهم. كده ما تستناش حد.
