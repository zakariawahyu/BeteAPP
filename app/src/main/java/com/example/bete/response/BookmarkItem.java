package com.example.bete.response;

import com.google.gson.annotations.SerializedName;

public class BookmarkItem{

	@SerializedName("penulis")
	private String penulis;

	@SerializedName("foto_berita")
	private String fotoBerita;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("foto_profile")
	private String fotoProfile;

	@SerializedName("judul_berita")
	private String judulBerita;

	@SerializedName("isi_berita")
	private String isiBerita;

	@SerializedName("id_bookmark")
	private String idBookmark;

	@SerializedName("password")
	private String password;

	@SerializedName("nama_pengguna")
	private String namaPengguna;

	@SerializedName("id_berita")
	private String idBerita;

	@SerializedName("id_pengguna")
	private String idPengguna;

	@SerializedName("tanggal_posting")
	private String tanggalPosting;

	@SerializedName("username")
	private String username;

	public void setPenulis(String penulis){
		this.penulis = penulis;
	}

	public String getPenulis(){
		return penulis;
	}

	public void setFotoBerita(String fotoBerita){
		this.fotoBerita = fotoBerita;
	}

	public String getFotoBerita(){
		return fotoBerita;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setFotoProfile(String fotoProfile){
		this.fotoProfile = fotoProfile;
	}

	public String getFotoProfile(){
		return fotoProfile;
	}

	public void setJudulBerita(String judulBerita){
		this.judulBerita = judulBerita;
	}

	public String getJudulBerita(){
		return judulBerita;
	}

	public void setIsiBerita(String isiBerita){
		this.isiBerita = isiBerita;
	}

	public String getIsiBerita(){
		return isiBerita;
	}

	public void setIdBookmark(String idBookmark){
		this.idBookmark = idBookmark;
	}

	public String getIdBookmark(){
		return idBookmark;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNamaPengguna(String namaPengguna){
		this.namaPengguna = namaPengguna;
	}

	public String getNamaPengguna(){
		return namaPengguna;
	}

	public void setIdBerita(String idBerita){
		this.idBerita = idBerita;
	}

	public String getIdBerita(){
		return idBerita;
	}

	public void setIdPengguna(String idPengguna){
		this.idPengguna = idPengguna;
	}

	public String getIdPengguna(){
		return idPengguna;
	}

	public void setTanggalPosting(String tanggalPosting){
		this.tanggalPosting = tanggalPosting;
	}

	public String getTanggalPosting(){
		return tanggalPosting;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"BookmarkItem{" + 
			"penulis = '" + penulis + '\'' + 
			",foto_berita = '" + fotoBerita + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",foto_profile = '" + fotoProfile + '\'' + 
			",judul_berita = '" + judulBerita + '\'' + 
			",isi_berita = '" + isiBerita + '\'' + 
			",id_bookmark = '" + idBookmark + '\'' + 
			",password = '" + password + '\'' + 
			",nama_pengguna = '" + namaPengguna + '\'' + 
			",id_berita = '" + idBerita + '\'' + 
			",id_pengguna = '" + idPengguna + '\'' + 
			",tanggal_posting = '" + tanggalPosting + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}
