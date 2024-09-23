package com.example.exodia.submit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import com.example.exodia.common.domain.BaseTimeEntity;
import com.example.exodia.common.domain.DelYN;
import com.example.exodia.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "del_yn = 'N'")
public class SubmitLine extends BaseTimeEntity {
	@Id
	@Column(nullable = false, length = 12)
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SubmitStatus submitStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "del_yn", nullable = false)
	private DelYN delYn = DelYN.N;

	@Column(nullable = false)
	private String user_id;

	@Column(nullable = false)
	private String department_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "submit_id", nullable = false)
	private Submit submit;

}
