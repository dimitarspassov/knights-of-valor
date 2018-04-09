package com.dspassov.kovapi.services;

import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.models.HeroServiceModel;
import com.dspassov.kovapi.areas.game.services.HeroService;
import com.dspassov.kovapi.areas.users.entities.User;
import com.dspassov.kovapi.areas.users.models.RegisterUserBindingModel;
import com.dspassov.kovapi.repositories.UserRepository;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final HeroService heroService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           ModelMapper modelMapper, HeroService heroService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.heroService = heroService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(email);
    }


    @Override
    public String save(RegisterUserBindingModel model) {

        if (!model.getPassword().equals(model.getConfirmPassword())) {
            throw new IllegalArgumentException(ResponseMessageConstants.PASSWORDS_MISMATCH);
        }

        if (this.userRepository.findByUsername(model.getUsername()) != null) {
            throw new IllegalArgumentException(ResponseMessageConstants.EXISTING_USER);
        }

        if (this.heroService.findHeroByName(model.getHeroName()) != null) {
            throw new IllegalArgumentException(ResponseMessageConstants.EXISTING_HERO);
        }

        model.setPassword(this.passwordEncoder.encode(model.getPassword()));
        User user = this.modelMapper.map(model, User.class);
        HeroServiceModel newHero = this.heroService.createNewHero(model.getHeroName());
        Hero hero = this.modelMapper.map(newHero, Hero.class);
        user.setHero(hero);
        this.userRepository.save(user);

        return ResponseMessageConstants.REGISTRATION_SUCCESSFUL;
    }

}
