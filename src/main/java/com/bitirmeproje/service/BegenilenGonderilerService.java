package com.bitirmeproje.service;
import com.bitirmeproje.model.BegenilenGonderiler;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.BegenilenGonderilerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bitirmeproje.dto.BegenilenGonderilerDto;

import java.util.List;

@Service
public class BegenilenGonderilerService {
    private final BegenilenGonderilerRepository repository;
    public BegenilenGonderilerService(BegenilenGonderilerRepository repository)//Repoyla bağlılığı sağlandı heralde
    {
        this.repository=repository;
    }
    @Transactional //hata olursa rollback
    public void begeniEkle(Gonderiler gonderi, User kullanici)
    {
        if(!repository.existsByGonderiIdAndKullaniciId(gonderi, kullanici))
        {
            BegenilenGonderiler begeni=new BegenilenGonderiler();
            begeni.setGonderiId(gonderi);
            begeni.setKullaniciId(kullanici);
            repository.save(begeni);
        }
    }
    @Transactional
    public void begeniKaldir(Gonderiler gonderi,User kullanici) //gönderiden beğeniyi kaldır
    {
        repository.deleteByGonderiIdAndKullaniciId(gonderi, kullanici);
    }
    public int gonderiBegeniSayisi(Gonderiler gonderi) //gönderinin begeni sayısı
    {
        return repository.countByGonderiId(gonderi);
    }
    public List<BegenilenGonderilerDto> kullanicininBegenileri(User kullanici) //belirli bir kullanıcının beğendiği gönderiler
    {
       return repository.findByKullaniciId(kullanici)
               .stream()
               .map(begeni->new BegenilenGonderilerDto(begeni.getGonderiId().getGonderiId()))
               .toList();
    }
   /* public List<BegenilenGonderilerDto> populerGonderiler()
    {
        return repository.findTop10ByOrderByBegeniSayisi()
                .stream()
                .map(begeni->new BegenilenGonderilerDto(begeni.getGonderiId()))
                .toList();
    }*/


}
