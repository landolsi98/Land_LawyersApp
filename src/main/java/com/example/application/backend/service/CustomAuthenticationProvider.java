package com.example.application.backend.service;


/*
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    public CustomAuthenticationProvider() {
        super();
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        final String user = authentication.getName();
        String password = authentication.getCredentials().toString();

        User userLoggedIn = userService.findUserByUsername(user);
        System.out.println(" user msajel " + userLoggedIn.getAuthority());
        if (userLoggedIn != null) {
            final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority(userLoggedIn.getAuthority().getRol()));

            final UserDetails principal = new org.springframework.security.core.userdetails.User(user, password, grantedAuths);
            final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);

            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.setAttribute("user", userLoggedIn);
            return auth;
        }



        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(final Class authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
*/