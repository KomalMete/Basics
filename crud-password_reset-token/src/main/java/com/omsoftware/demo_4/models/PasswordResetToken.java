package com.omsoftware.demo_4.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private Users users;

    private Date expiryDate;

    public PasswordResetToken(String token,Users users) {
        this.token = token;
        this.users = users;
        this.expiryDate = calculateExpiryDate(24 * 60);
    }

    private Date calculateExpiryDate(int expiryTimeInMinute)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinute);
        return new Date(cal.getTime().getTime());
    }
}
