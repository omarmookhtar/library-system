# علي — Frontend Lead

## دورك
أنت قائد الفرونت (يحيى وسيف معاك). أنت اللي بتعمل **الأساس** اللي الاتنين بيبنوا عليه، وصفحات الاستعارة.

## التقنية المقترحة
React + Vite + React Router + Axios (لو عايز حاجة تانية اتفق مع عمر، المهم تكلم الـ Gateway بس).

## البيئة
- كل الطلبات على `http://localhost:8080` (الـ Gateway) **بس**
- حط الـ base URL في `.env`: `VITE_API_URL=http://localhost:8080`

## اللي بتستلمه من عمر
- `docs/CONTRACT.md` (كل الـ endpoints والـ roles)
- شكل الـ login response: `{ "token": "...", "role": "MEMBER", "userId": 1, "name": "..." }`
- شكل الـ error: `{status, message, errors}`
- بعد الأسبوع الأول: Postman collection من سيف

## اللي هتكتبه (الأساس)
1. مشروع Vite + هيكل فولدرات: `api/`, `pages/`, `components/`, `context/`
2. `api/axios.js`: instance + interceptor بيحط `Authorization: Bearer <token>` + يعامل 401 (يرجّع للّوجين) و403
3. `AuthContext`: login/logout، تخزين التوكن والـ role
4. `ProtectedRoute` (حسب الـ role)، Layout + Navbar بتتغير حسب الـ role
5. صفحات Login و Register
6. صفحات Borrowing: "استعاراتي" (Member)، "تسجيل استعارة/إرجاع" (Librarian)

**أول يومين:** سلّم الأساس ليحيى وسيف عشان يبدأوا يبنوا عليه (ادّيهم الـ axios والـ AuthContext جاهزين).

## لازم تتعلمه
- React Router (nested + protected routes)
- Axios interceptors، Context API
- التعامل مع JWT (decode للـ role بس، مش للأمان)
- عرض أخطاء الـ validation اللي راجعة من الباك (`errors` map)

## الدوكيومنتس
- Screenshots لصفحات الفرونت للتقرير
- تراجع PRs يحيى وسيف قبل ما تبعتهم لعمر

## نصيحة
لحد ما الباك يجهز، اشتغل بـ mock data بنفس شكل الـ CONTRACT بالظبط. وقت التوصيل بتغير الـ base URL بس.
