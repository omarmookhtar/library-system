# يحيى — Frontend: Catalog + Users

## دورك
صفحات الكتب وإدارة المستخدمين، فوق الأساس اللي علي هيسلمهولك.

## اللي بتستلمه
- من علي: مشروع الأساس (axios, AuthContext, ProtectedRoute, Layout)
- من عمر: `CONTRACT.md`

## اللي هتكتبه
**Catalog:**
- صفحة قايمة الكتب + بحث (title/author/category) — كل الـ roles
- صفحة تفاصيل كتاب
- فورم إضافة/تعديل كتاب — ADMIN/LIBRARIAN بس
- زرار حذف — ADMIN بس (واعرض رسالة الـ 409 لو الكتاب مستعار)

**Users (ADMIN):**
- قايمة المستخدمين، تغيير الـ role، حذف مستخدم
- صفحة "ملفي" (`/api/users/me`)

## لازم تتعلمه
- React: hooks (useState/useEffect)، forms وvalidation
- استدعاء الـ API عن طريق `api/axios.js` بتاع علي (ما تعملش axios جديد)
- إخفاء/إظهار الأزرار حسب الـ role

## الدوكيومنتس (مهمتك الأساسية في الورق)
- **Use case diagram** (الـ actors: Admin, Librarian, Member + كل الـ use cases)
- **SRS:** الـ Functional Requirements لـ Users و Catalog (مرقمة FR-01…)
  - مثال: FR-01: "A librarian can add a new book to the catalog."
- وصف أهم 3 use cases (actor, preconditions, main flow, alternative flows)

## نصيحة
ابدأ بالـ Use case diagram والـ SRS في أول أسبوع (لسه الباك مش جاهز)، وبعدها اشتغل على الصفحات.
