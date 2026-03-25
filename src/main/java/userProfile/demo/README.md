## 🔐 Role-Based Authentication

This project uses Spring Security with JWT to enforce role-based access control:

- **USER**
  - Can sign up and sign in
  - Can create their own profile (`POST /profiles/create`)
  - Can view their own profile (`GET /profiles/me`)

- **ADMIN**
  - Can view all profiles (`GET /profiles/all`)
  - Can list users without profiles (`GET /api/v1/auth/no-profile`)

### Example Authorization Rules
Configured in `WebSecurityConfig`:

```java
http.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/v1/auth/**").permitAll()
    .requestMatchers("/profiles/me").hasRole("USER")
    .requestMatchers("/profiles/create").hasRole("USER")
    .requestMatchers("/profiles/all").hasRole("ADMIN")
    .anyRequest().authenticated()
);

