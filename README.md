# newway-api
rest api for newway application

- ### Security Configuration

All request allowed: `http.authorizeRequests().anyRequest().permitAll();`

- #### Login Explanation

    - We implemented `UserDetailsService` as `DeveloperUserDetailsService`. With this implementation we gave the `username` and `password` control to the `Spring Security`. 
    - After that we will call `UsernamePasswordAuthenticationToken` from `loginDeveloper` method. Accordingly we will pass the `username` and  `password` to the `Spring Security`. Spring Security will check given credentials via `DeveloperUserDetailsService`.
    - Afterwards if credentials okay, we will create a token via `TokenProvider.createToken` using `io.jsonwebtoken` library. Finally we will return a token to user.
