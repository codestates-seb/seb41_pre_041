package seb4141preproject.security.auth.refreshtoken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seb4141preproject.security.audit.DataTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "REFRESH_TOKEN")
@Entity
public class RefreshToken extends DataTable {

    @Id
    @Column(name = "RT_KEY", length = 700)
    private String key;

    @Column(name = "RT_VALUE", length = 700)
    private String value;

    @Builder

    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
