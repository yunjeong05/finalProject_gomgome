package com.ezen.gomgome.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_gomg_faq")
@SequenceGenerator(
		name="FaqSequenceGenerator",
		sequenceName="faq_seq",
		initialValue=1,
		allocationSize=1
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Data
public class FAQ {
	@Id
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE,
			generator="FaqSequenceGenerator"
	)
	private int faqNo;
	private String faqAdmin;
	private String faqTitle;
	private LocalDateTime faqRegdate = LocalDateTime.now();
	private String faqAnswer;
	private String faqHide; 
}
