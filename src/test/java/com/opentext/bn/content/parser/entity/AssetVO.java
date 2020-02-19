package com.opentext.bn.content.parser.entity;

import lombok.Data;

@Data
public class AssetVO {

	private String assetId = null;
	private String assetName = null;
	private String assetType = null;
	private String assetDescription = null;
	private PayloadRefVO xifInfo = null;

}
