package com.zjianhao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Photo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "photo", catalog = "album")
public class Photo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Album album;
	private String photoName;
	private String photoUrl;
	private Timestamp photoDate;
	private Float latitude;
	private Float longitude;

	// Constructors

	/** default constructor */
	public Photo() {
	}

	/** minimal constructor */
	public Photo(Album album, String photoName, String photoUrl) {
		this.album = album;
		this.photoName = photoName;
		this.photoUrl = photoUrl;
	}

	/** full constructor */
	public Photo(Album album, String photoName, String photoUrl,
			Timestamp photoDate, Float latitude, Float longitude) {
		this.album = album;
		this.photoName = photoName;
		this.photoUrl = photoUrl;
		this.photoDate = photoDate;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "album_id", nullable = false)
	public Album getAlbum() {
		return this.album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	@Column(name = "photo_name", nullable = false, length = 60)
	public String getPhotoName() {
		return this.photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	@Column(name = "photo_url", nullable = false, length = 80)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "photo_date", length = 19)
	public Timestamp getPhotoDate() {
		return this.photoDate;
	}

	public void setPhotoDate(Timestamp photoDate) {
		this.photoDate = photoDate;
	}

	@Column(name = "latitude", precision = 12, scale = 0)
	public Float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", precision = 12, scale = 0)
	public Float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	

}