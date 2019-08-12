package com.blackbeard.teach.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

@Getter
@Setter
public class PlayerModel {
	private int				rec;
	@NotNull(message="Hero name cannot be blank")
	@Length(max=30, min=3, message="Minimum length of hero name is 3 and Max 30")
	private String			name;
	@NotNull(message= "Player class cannot be blank")
	private String			pClass;
	@Digits(integer=3, fraction=0, message="The value of level cannot be more than 3 digits")
	@Min(value=0, message="Min value of level cannot be less than 0")
	@Max(value=100, message="Max value of level cannot be more than 100")
	private int				level;
	@Digits(integer=5, fraction=0, message="Experience cannot be more than 5 digits")
	@Min(value=0, message="Experience cannot be less than 0")
	private int XP;
	@Digits(integer=3, fraction=0, message="Attack cannot be more than 3 digits")
	@Min(value=0, message="Attack cannot be less than 0")
	private int				attack;
	@Digits(integer=3, fraction=0, message="Defence cannot be more than 3 digits")
	@Min(value=0, message="Defence cannot be less than 0")
	private int				defence;
	@Digits(integer=3, fraction=0, message="HP cannot be more than 3 digits")
	@Min(value=0, message="HP cannot be less than 0")
	private int HP;
	private int				y;
	private int				x;

	public PlayerModel() {
	}

	public PlayerModel(String name, String pClass) {
		this.name = name;
		this.pClass = pClass;
	}

	public PlayerModel(String name, String pclass, int level, int XP, int Attack,
					   int defence, int HP) {
		this.name = name;
		this.pClass = pclass;
		this.level = level;
		this.XP = XP;
		this.attack = Attack;
		this.defence = defence;
		this.HP = HP;
		this.y = 0;
		this.x = 0;
	}
	public void				setPosition(int y, int x) { this.y = y; this.x = x; }
}	
