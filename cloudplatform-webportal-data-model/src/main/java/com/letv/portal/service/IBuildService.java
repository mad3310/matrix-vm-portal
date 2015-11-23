package com.letv.portal.service;

import java.util.List;

import com.letv.portal.model.BuildModel;




/**Program Name: IBuildService <br>
 * Description:  python api 创建mcluster及db等创建过程记录<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IBuildService extends IBaseService<BuildModel> {

	public List<BuildModel> selectByMclusterId(Long mclusterId);

	public void initStatus(Long mclusterId);
	
	public void updateByStatus(BuildModel buildModel);

	public void deleteByMclusterId(Long mclsuterId);

	public List<BuildModel> selectByDbId(Long dbId);

	public void updateByStep(BuildModel nextBuild);
	
	public int getStepByDbId(Long dbId);
}
