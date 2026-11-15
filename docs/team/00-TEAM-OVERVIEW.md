# نظام المكتبة — توزيع الفريق (SW2)

## الفكرة
عمر بيجهز **الأساس** (Eureka، Gateway، الأمان، docker-compose، القالب). باقي الفريق **بياخد الجاهز وبيكتب الجزء بتاعه بس**. مفيش حد بيعمل ربط، والربط كله عند عمر.

## الجدول

| الاسم | الدور | الشغل الأساسي | في docs/ |
|---|---|---|---|
| عمر | Team Leader / Backend | user-service + Eureka + Gateway + Security + Docker + الربط | Architecture diagram + SRS (القسم 1-2) + README + التقرير النهائي |
| حسن | Backend (قوي) | borrowing-service | Sequence diagram + Activity diagram + 2 OCL |
| عماد | Backend (قوي) | fines-service + AOP | Class diagram + تجميع الـ OCL |
| مازن | Backend (متوسط) | catalog-service | ERD (الكل) + 1 OCL |
| علي | Frontend Lead (الأقوى) | أساس الفرونت + Auth + صفحات Borrowing | UI screenshots للتقرير |
| يحيى | Frontend | صفحات Catalog + إدارة المستخدمين | Use case diagram + SRS (FR للـ Users والـ Catalog) |
| سيف | Frontend | صفحات Fines/Reports + اختبار | Postman collection + SRS (FR للـ Borrowing/Fines + NFR) |

## قواعد الفريق (مهمة)

1. **كل واحد بيشتغل جوه فولدره بس.** أي حاجة برة فولدرك اطلبها من عمر.
2. **`docs/CONTRACT.md` هو الحَكَم.** لو عايز تغير endpoint أو DTO، قول لعمر الأول.
3. **ابدأ من `service-template`**، ما تبدأش من الصفر.
4. **كل يوم commit** باسمك على branch خاص بيك + Pull Request. (الدكتورة هتفحص الـ commits.)
5. **قبل ما تقول "خلصت"**: السيرفس يشتغل، يظهر في Eureka، Swagger شغال، وتجربته من Postman عن طريق الـ Gateway.
6. لو اتعطلت أكتر من ساعتين، اسأل. ما تضيعش يوم.

## الـ Definition of Done لأي سيرفس
- [ ] متسجل في Eureka
- [ ] Controller → Service → Repository، وDTOs بدل Entities
- [ ] `@Valid` على كل request
- [ ] 4 business rules على الأقل شغالة (409/400 مظبوطة)
- [ ] Swagger فيه وصف وأمثلة لكل endpoint
- [ ] Dockerfile بيبني (`docker compose up --build`)
- [ ] OCL الخاصة بيك مكتوبة ومكتوب مكانها في الكود
- [ ] كل الـ endpoints موجودة في Postman

## ترتيب الأسابيع
| الأسبوع | الهدف |
|---|---|
| 1 | الكل يقرأ ملفه ويجهز بيئته، عمر يجهز الأساس. SRS + الـ Contract. ERD لكل سيرفس. |
| 2 | كل واحد سيرفسه شغال (CRUD + Rules). الفرونت: الأساس + Auth + Mock data. |
| 3 | الربط (Feign) + AOP + OCL + الفرونت يتوصل بالـ Gateway. |
| 4 | Docker + Postman + Diagrams + التقرير + بروفة المناقشة. |

## للمناقشة
أي واحد ممكن يتسأل عن أي حاجة. في آخر أسبوع كل واحد يشرح سيرفسه للباقي (10 دقايق) ويتعلم الصورة الكاملة.
