package com.example.bete.response;
import com.google.gson.annotations.SerializedName;

public class ForumItem{

	@SerializedName("penulis")
	private String penulis;

	@SerializedName("id_forum")
	private String idForum;

	@SerializedName("foto_berita")
	private String fotoBerita;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("id_berita")
	private String idBerita;

	@SerializedName("judul_berita")
	private String judulBerita;

	@SerializedName("tanggal_posting")
	private String tanggalPosting;

	@SerializedName("isi_berita")
	private String isiBerita;

	public void setPenulis(String penulis){
		this.penulis = penulis;
	}

	public String getPenulis(){
		return penulis;
	}

	public void setIdForum(String idForum){
		this.idForum = idForum;
	}

	public String getIdForum(){
		return idForum;
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

	public void setIdBerita(String idBerita){
		this.idBerita = idBerita;
	}

	public String getIdBerita(){
		return idBerita;
	}

	public void setJudulBerita(String judulBerita){
		this.judulBerita = judulBerita;
	}

	public String getJudulBerita(){
		return judulBerita;
	}

	public void setTanggalPosting(String tanggalPosting){
		this.tanggalPosting = tanggalPosting;
	}

	public String getTanggalPosting(){
		return tanggalPosting;
	}

	public void setIsiBerita(String isiBerita){
		this.isiBerita = isiBerita;
	}

	public String getIsiBerita(){
		return isiBerita;
	}

	@Override
 	public String toString(){
		return 
			"ForumItem{" + 
			"penulis = '" + penulis + '\'' + 
			",id_forum = '" + idForum + '\'' + 
			",foto_berita = '" + fotoBerita + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",id_berita = '" + idBerita + '\'' + 
			",judul_berita = '" + judulBerita + '\'' + 
			",tanggal_posting = '" + tanggalPosting + '\'' + 
			",isi_berita = '" + isiBerita + '\'' + 
			"}";
		}
}
