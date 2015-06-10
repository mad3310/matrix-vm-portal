package com.letv.portal.controller.cloudgfs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.result.ResultObject;

/**Program Name: SkipController <br>
 * Description:  用于页面跳转       list、detail、form、……<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月8日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller("gfsSkip")
public class SkipController {
	
	@RequestMapping(value ="/list/gfs/peer",method=RequestMethod.GET)
	public ModelAndView toGfsPeer(ModelAndView mav,HttpServletRequest request){
		mav.setViewName("/cloudgfs/peer_list");
		return mav;
	}
	@RequestMapping(value ="/list/gfs/volume",method=RequestMethod.GET)
	public ModelAndView toGfsVolume(ModelAndView mav,HttpServletRequest request){
		mav.setViewName("/cloudgfs/volume_list");
		return mav;
	}
	@RequestMapping(value ="/detail/gfs/volume/{volName}",method=RequestMethod.GET)
	public ModelAndView toGfsVolumeDetail(@PathVariable String volName,ModelAndView mav,HttpServletRequest request){
		mav.addObject("volName",volName);
		mav.setViewName("/cloudgfs/volume_detail");
		return mav;
	}
}
