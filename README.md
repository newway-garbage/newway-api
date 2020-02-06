# newway-api
rest api for newway application

- ### Security Configuration

All request allowed: `http.csrf().disable().authorizeRequests().anyRequest().permitAll();`

- #### Login Explanation

    - We implemented `UserDetailsService` as `DeveloperUserDetailsService`. With this implementation we gave the `username` and `password` control to the `Spring Security`. 
    - After that we will call `UsernamePasswordAuthenticationToken` from `loginDeveloper` method. Accordingly we will pass the `username` and  `password` to the `Spring Security`. Spring Security will check given credentials via `DeveloperUserDetailsService`.
    - Afterwards if credentials okay, we will create a token via `TokenProvider.createToken` using `io.jsonwebtoken` library. Finally we will return a token to user.
    
- #### Security Configuration Explanation

    - We gave the restriction about our endpoints at `SecurityConfiguration.configure` method.
    - Now every request who it will be come to our app will goes a filter(`JWTFilter.doFilter`). In that method app will be check if has token and that token is it valid.
    - Afterwards app will make a decision about returning a proper response or `403 Forbiden`.
