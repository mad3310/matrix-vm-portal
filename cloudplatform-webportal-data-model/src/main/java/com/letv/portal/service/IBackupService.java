package com.letv.portal.service;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.BackupResultModel;

/**Program Name: IBackupService <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年1月4日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IBackupService extends IBaseService<BackupResultModel> {

	void deleteByMclusterId(Long id);

	public void deleteOutDataByIndex(Map<String, Object> map);
	public List<Map<String, Object>> selectExtremeIdByMonitorDate(
			Map<String, Object> map);

	List<BackupResultModel> selectByStatusAndDateOrderByMclusterId(
			Map<String, Object> params);

}
