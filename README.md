# Grand Vista Hotel — Reservation System (Web Edition)

A Spring Boot + Thymeleaf + MySQL rewrite of the original AWT desktop hotel
reservation system, so it can run live in a browser.

## What's in here
- `src/main/java/com/hotel/model` — data classes (unchanged from the original)
- `src/main/java/com/hotel/dao` — database access, rewritten with Spring's
  `JdbcTemplate` for safe connection pooling
- `src/main/java/com/hotel/controller` — replaces the AWT screens: handles
  admin login/dashboard/room management/reports, and customer
  register/login/booking/payment
- `src/main/resources/templates` — the web pages (Thymeleaf)
- `schema.sql` — your actual database dump (tables + existing rooms, customers,
  bookings, payments, and admin login). Run this once against a fresh
  database to recreate everything exactly as it is on your machine now.
- `Dockerfile` — used by Render to build and run the app

## 1. Local setup (optional, to test before deploying)
1. Install MySQL locally and create a database:
   ```sql
   CREATE DATABASE hotel_db;
   ```
2. Import your data:
   ```bash
   mysql -u root -p hotel_db < schema.sql
   ```
3. From the project root:
   ```bash
   mvn spring-boot:run
   ```
4. Visit `http://localhost:8080`

Default admin login: **username** `admin`, **password** `admin123` — change
this after your first login.

## 2. Push to GitHub
```bash
cd hotel-web
git init
git remote add origin https://github.com/Subha-Dhanusha/Hotel-reservation-system.git
git pull origin main --allow-unrelated-histories
git add .
git commit -m "Convert to Spring Boot web app"
git branch -M main
git push -u origin main
```

## 3. Set up a free MySQL database (Aiven)
1. Go to https://aiven.io and sign up (no credit card needed).
2. Create a new service → MySQL → select the **Free** plan.
3. Once it's running, open the service and copy the connection details:
   `Host`, `Port`, `User`, `Password`, and default database name.
4. Import your data into it. With the `mysql` CLI:
   ```bash
   mysql --host=<aiven-host> --port=<aiven-port> --user=<aiven-user> -p \
         --ssl-mode=REQUIRED <database-name> < schema.sql
   ```
   Or use the Aiven console / a GUI tool like TablePlus or DBeaver and run
   `schema.sql` against it. This recreates your tables and carries over your
   existing rooms, customers, bookings, payments, and admin login.

## 4. Deploy to Render
1. Go to https://render.com and sign up / log in.
2. **New +** → **Web Service** → connect your GitHub repo
   (`Subha-Dhanusha/Hotel-reservation-system`).
3. Render should detect the `Dockerfile` automatically. If asked, set:
   - **Environment**: Docker
   - **Region**: closest to you
   - **Instance type**: Free
4. Add these **Environment Variables** (Render dashboard → your service →
   Environment):
   | Key | Value |
   |---|---|
   | `DB_URL` | `jdbc:mysql://<aiven-host>:<aiven-port>/<database-name>?sslMode=REQUIRED` |
   | `DB_USERNAME` | your Aiven MySQL username |
   | `DB_PASSWORD` | your Aiven MySQL password |
5. Click **Create Web Service**. Render will build the Docker image and
   deploy it — this takes a few minutes the first time.
6. Once it's live, Render gives you a URL like
   `https://hotel-reservation-system.onrender.com` — that's your live app.

**Note:** Render's free tier spins the service down after inactivity, so
the first request after a while will take ~30-60 seconds to wake up. This
is normal and fine for a portfolio/demo project.

## Notes on what changed from the desktop version
- AWT screens → Thymeleaf web pages + Spring MVC controllers
- The old `Session` class (a single shared static field) was replaced with
  per-visitor `HttpSession`, since a web app has many users at once, not
  just one desktop user
- DAOs now use `JdbcTemplate` instead of raw `DriverManager` connections,
  which are properly returned to a small connection pool — important on a
  free-tier database with a limited connection count
- Fixed an inconsistency in the original `CustomerDAO` where some queries
  referenced a `customer` table and others `customers`
