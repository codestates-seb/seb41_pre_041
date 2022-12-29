package seb4141preproject.security.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "REFRESH_TOKEN")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "RT_KEY", length = 700)
    private String key;
    @Column(name = "RT_VALUE", length = 700)
    private String value;

//    private Status status = Status.ACTIVE;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String value) {
        this.value = value;
        return this;
    }
}
