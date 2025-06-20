package vn.edu.hcmuaf.st.DACN_BookStore_2025.service.impl;


import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.oauth2.CustomOAuth2User;

@Service
public class CustomOAuth2UserServiceImp extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //clientName can be Google or Facebook
        String clientName= userRequest.getClientRegistration().getClientName();
        OAuth2User user = super.loadUser(userRequest);
        return new CustomOAuth2User(user, clientName);
    }
}