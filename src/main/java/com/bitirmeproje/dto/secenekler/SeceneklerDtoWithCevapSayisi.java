package com.bitirmeproje.dto.secenekler;

public class SeceneklerDtoWithCevapSayisi extends SeceneklerDto {
    private long secenekCevapSayisi;

    public SeceneklerDtoWithCevapSayisi() {}

    public SeceneklerDtoWithCevapSayisi(long secenekCevapSayisi, int seceneklerId, int anketId, String secenekMetni) {
        super(seceneklerId, anketId, secenekMetni);
        this.secenekCevapSayisi = secenekCevapSayisi;
    }

    public long getSecenekCevapSayisi() {
        return secenekCevapSayisi;
    }

    public void setSecenekCevapSayisi(long secenekCevapSayisi) {
        this.secenekCevapSayisi = secenekCevapSayisi;
    }
}
