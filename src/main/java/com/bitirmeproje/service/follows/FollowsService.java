package com.bitirmeproje.service.follows;

import com.bitirmeproje.dto.follows.PopulerKullaniciDto;
import com.bitirmeproje.dto.user.UserDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Follows;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.FollowsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class FollowsService implements IFollowsService {
    private final FollowsRepository followsRepository;
    private final GetUserByToken getUserByToken;
    private final FindUser<Integer> findUser;
    private final IEntityDtoConverter<User, UserDto> entityDtoConvert;

    public FollowsService(FollowsRepository followsRepository, GetUserByToken getUserByToken,
                          FindUser<Integer> findUser,
                          IEntityDtoConverter<User, UserDto> entityDtoConvert)
    {
        this.followsRepository=followsRepository;
        this.getUserByToken=getUserByToken;
        this.findUser=findUser;
        this.entityDtoConvert=entityDtoConvert;
    }

    // Kullanıcının istediği kişiyi takip et
    public void followUser(int followingId) {
        User follower = getUserByToken.getUser();

        User followingUser = findUser.findUser(followingId);

        // Kullanıcı kendi kendisini takip edemez
        if (follower.getKullaniciId() == followingUser.getKullaniciId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Kendi kendinizi takip edemezsiniz!");
        }

        // Kullanıcı zaten takip ediyorsa hata döndür
        if (followsRepository.findByFollowerAndFollowing(follower, followingUser).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu kullanıcıyı zaten takip ediyorsunuz!");
        }

        // Yeni takip kaydı oluştur
        Follows follows = new Follows();
        follows.setTakipEdenKullaniciId(follower);
        follows.setTakipEdilenKullaniciId(followingUser);
        follows.setTakipEtmeTarihi(LocalDate.now());

        followsRepository.save(follows);
    }

    // Kullanıcının istediği kişiyi takipten çık
    public void unfollowUser(int takipEdilenId) {
        User follower = getUserByToken.getUser();

        User followingUser = findUser.findUser(takipEdilenId);

        // Kullanıcı kendi kendisini takipten çıkamaz çünkü zaten takip etmiyor
        if (follower.getKullaniciId() == followingUser.getKullaniciId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Kendi kendinizi takipten çıkaramazsınız!");
        }

        // Kullanıcının gerçekten takip edip etmediğini kontrol et
        Optional<Follows> followRecordOptional = followsRepository.findByFollowerAndFollowing(follower, followingUser);

        if (followRecordOptional.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu kullanıcıyı takip etmiyorsunuz!");
        }

        // Takip kaydını veritabanından sil
        followsRepository.delete(followRecordOptional.get());
    }

    // Kullanıcıyı takip eden kişileri getir.
    public Map<String, Object> getFollowers() {
        return getFollow(followsRepository::findByFollowersUserId);
    }

    // Kullanıcının takip ettiği kişileri getir.
    public Map<String, Object> getFollowing() {
        return getFollow(followsRepository::findByFollowingUserId);
    }

    public List<PopulerKullaniciDto> populerKullanicilariGetir() {
        return followsRepository.findMostFollowedUsers();
    }

    // getFollows ve getFollowing api'lerini ortak metoda aldık.
    private Map<String, Object> getFollow(Function<Integer, List<User>> fetchUsersFunction){
        User user = getUserByToken.getUser();

        List<User> following = fetchUsersFunction.apply(user.getKullaniciId());

        List<UserDto> users = following.stream()
                .map(entityDtoConvert::convertToDTO)
                .toList();

        int followersCount = following.size();

        Map<String, Object> response = new HashMap<>();
        response.put("followCount", followersCount);
        response.put("follow", users);

        return response;
    }
}
