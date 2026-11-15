# library-system

نظام إدارة مكتبة (Library Management) — مشروع SW2 — Spring Boot Microservices.

## Services

| Service | Port | Database |
|---|---|---|
| eureka-server | 8761 | — |
| api-gateway | 8080 | — |
| user-service | 8081 | user_db |
| catalog-service | 8082 | catalog_db |
| borrowing-service | 8083 | borrowing_db |
| fines-service | 8084 | fines_db |

## Database
PostgreSQL (Docker) على `localhost:5433` (جوه شبكة Docker: 5432).

## Roles
`ADMIN`, `LIBRARIAN`, `MEMBER`

## تشغيل النظام
```
docker compose up --build
```
- Eureka dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Swagger UI لكل سيرفس: `http://localhost:<port>/swagger-ui.html`

## للفريق
اقرأ بالترتيب: `docs/SETUP.md` ثم `docs/CONTRACT.md` ثم ملفك في `docs/team/`.
