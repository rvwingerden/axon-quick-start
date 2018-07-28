package com.shadem.labs.vesselcall.query.myview;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author albert
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = { "id" })
public class Visit {

	@Id
	private String id;
	
	private String vesselName;
	
	@Setter
	private String portCode;
	
	@Setter
	private boolean status;
	
	@Setter
	private String carrier;
}