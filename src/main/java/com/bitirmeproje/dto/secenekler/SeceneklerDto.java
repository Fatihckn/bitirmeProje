package com.bitirmeproje.dto.secenekler;

public class SeceneklerDto {
    protected int seceneklerId;

    protected int anketId;

    protected String secenekMetni;

    public SeceneklerDto() {}

    public SeceneklerDto(int seceneklerId, int anketId, String secenekMetni) {
        this.seceneklerId = seceneklerId;
        this.anketId = anketId;
        this.secenekMetni = secenekMetni;
    }

    public int getSeceneklerId() {
        return seceneklerId;
    }

    public void setSeceneklerId(int seceneklerId) {
        this.seceneklerId = seceneklerId;
    }

    public int getAnketId() {
        return anketId;
    }

    public void setAnketId(int anketId) {
        this.anketId = anketId;
    }

    public String getSecenekMetni() {
        return secenekMetni;
    }

    public void setSecenekMetni(String secenekMetni) {
        this.secenekMetni = secenekMetni;
    }
}
