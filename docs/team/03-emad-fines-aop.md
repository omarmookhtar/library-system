# عماد — Backend: Fines/Reports + AOP

## دورك
سيرفس الغرامات والتقارير + **الـ AOP لكل المشروع** (بتكتبه مرة وعمر بيوزعه على الكل).

## فولدرك
`fines-service/` (انسخ `service-template`) — Port **8084**، DB `fines_db`

## اللي بتستلمه من عمر
- `service-template/` و `CONTRACT.md` (BR-6, BR-7)

## اللي هتكتبه
**1) الغرامات**
- Entity `Fine` (id, borrowingId, memberId, daysLate, amount, status[UNPAID/PAID], createdAt, paidAt)
- `POST /internal/fines/calculate` (من Borrowing عند الإرجاع: 5 جنيه × أيام التأخير)
- `GET /internal/fines/member/{id}/unpaid-total`
- `GET /api/fines/my`, `GET /api/fines`, `POST /api/fines/{id}/pay`

**2) التقارير**
- `GET /api/reports/overdue` (Feign لـ `borrowing-service`)
- `GET /api/reports/popular-books` و `/summary` (استعلامات تجميعية JPQL)

**3) الـ AOP (ملف منفصل تديه لعمر)**
ملف `aspects/` فيه:
- `@LogExecutionTime` (custom annotation) + `@Around` aspect يقيس الوقت ويطبع log
- `AuditAspect` بـ `@AfterReturning` أو `@Before` يسجل مين عمل إيه (من `UserContext`)
- `@AfterThrowing` اختياري لتسجيل الأخطاء

الشغل ده يتكتب في fines-service، ويتجرب، وبعدين عمر ينسخه لكل السيرفسز. (`spring-boot-starter-aop` موجودة في القالب.)

## لازم تتعلمه
- Spring AOP: Pointcut expressions (`execution(* com.library..service..*(..))`), `@Around` و`ProceedingJoinPoint`, `@annotation(...)`
- JPQL: `GROUP BY`, `COUNT`, `SUM`
- Feign (مثل حسن)
- (مناقشة) هتتسأل تشرح الـ pointcut حرف حرف، وممكن يطلبوا منك تغيره live

## الدوكيومنتس
- **Class diagram** لكل النظام (تاخد الـ entities من الكل)
- **تجميع الـ OCL** من الفريق في ملف واحد (5 على الأقل) + مكان تطبيق كل واحدة
- **شرح الـ pointcuts + صورة الـ logs** للتقرير
- OCL (2) على Fine:
```
context Fine
inv positiveAmount: self.amount >= 0

context FineService::pay(fineId : Long)
pre notPaid: Fine.allInstances()->any(f | f.id = fineId).status = 'UNPAID'
```

## نصيحة
ابدأ بالـ AOP **أول**، لأنه أخف وبيتوزع على الكل، وبعدين ارجع للغرامات.
