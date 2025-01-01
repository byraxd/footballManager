package com.example.footballManager.transfer.model;

import com.example.footballManager.player.model.Player;
import com.example.footballManager.team.model.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferStatus status;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "fromTeam_id", nullable = false)
    private Team fromTeam;

    @ManyToOne
    @JoinColumn(name = "toTeam_id", nullable = false)
    private Team toTeam;

    @Column(name = "transferCost", nullable = false)
    private Double transferCost;
}
