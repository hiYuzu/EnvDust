package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tcb.env.service.ISysflagService;
import com.tcb.env.util.DesUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tcb.env.Handler.WebsocketHandler;
import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Oranization;
import com.tcb.env.pojo.User;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SHAUtil;

/**
 * [功能描述]：User控制器
 */
@Controller
@RequestMapping("/UserController")
public class UserController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "UserController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(UserController.class);
    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;
    /**
     * 声明常量表服务
     */
    @Resource
    private ISysflagService sysflagService;

    /**
     * 声明资源文件
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    /**
     * 登录验证成功标记
     */
    private static final String SUCCESS = "success";

    /**
     * websocket处理器
     */
    @Resource
    private WebsocketHandler websocketHandler;

    /**
     * [功能描述]：验证登录信息
     */
    @RequestMapping(value = "/toMain", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView toMain(UserModel userModel, HttpSession httpsession, ModelAndView mv) {
        //允许刷新
        if (userModel != null && (userModel.getUserCode() == null || userModel.getUserCode().isEmpty())) {
            UserModel sessionUser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (sessionUser != null) {
                //跳转页面
                String result = validateUserNative(sessionUser, false, httpsession);
                if (SUCCESS.equals(result)) {
                    mv.setViewName("/html/main");
                    return mv;
                } else {
                    mv.addObject("error", result);
                    mv.setViewName("redirect:/index.html");
                    return mv;
                }
            }
        }
        String result = validateUserNative(userModel, true, httpsession);
        if (result.equals(SUCCESS)) {
            mv.setViewName("/html/main");
            return mv;
        } else {
            mv.addObject("error", result);
            mv.setViewName("redirect:/index.html");
            return mv;
        }
    }

    /**
     * [功能描述]：Ajax验证登录信息
     */
    @RequestMapping(value = "/validateUser", method = {RequestMethod.POST})
    @ResponseBody
    private ResultModel validateUser(UserModel userModel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        resultModel.setDetail(validateUserNative(userModel, true, httpsession));
        if (resultModel.getDetail() == SUCCESS) {
            UserModel seesionuser = new UserModel();
            User user = ConvertUser(userModel, true, httpsession);
            List<User> listuser = userService.getUser(user, false);
            seesionuser = ConvertUserModel(listuser.get(0));
            seesionuser.setUserPassword(user.getUserPassword());
            if (listuser != null && listuser.size() > 0) {
                httpsession.removeAttribute(DefaultArgument.LOGIN_USER);
                httpsession.setAttribute(DefaultArgument.LOGIN_USER, seesionuser);
                resultModel.setResult(true);
                resultModel.setDetail(seesionuser.getUserName());
            } else {
                resultModel.setResult(false);
                resultModel.setDetail("登录失败");
            }
        } else {
            resultModel.setResult(false);
        }
        return resultModel;
    }

    /**
     * [功能描述]：验证登录是否成功
     */
    private String validateUserNative(UserModel userModel, boolean encryFlag, HttpSession httpsession) {
        String result = null;
        try {
            User user = ConvertUser(userModel, encryFlag, httpsession);
            if (user != null && user.getUserCode() != null && !user.getUserCode().isEmpty()
                    || user.getUserPassword() != null || !user.getUserPassword().isEmpty()) {
                int count = userService.getCount(user, false);
                if (count > 0) {
                    result = SUCCESS;
                } else {
                    result = "用户名或密码错误！";
                }
            } else {
                result = "用户名或密码不能为空！";
            }

        } catch (Exception e) {
            result = "验证用户信息失败！";
            logger.error(LOG + "：验证用户信息失败，信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * [功能描述]：查询用户信息
     */
    @RequestMapping(value = "/queryUsers", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<UserModel> queryUsers(UserModel usermodel, HttpSession httpsession) {
        ResultListModel<UserModel> resultListModel = new ResultListModel<UserModel>();
        List<UserModel> listusermodel = new ArrayList<UserModel>();
        List<User> listuser = new ArrayList<User>();
        User user = ConvertUser(usermodel, true, httpsession);
        int count = userService.getCount(user, false);
        if (count > 0) {
            listuser = userService.getUser(user, false);
            for (User temp : listuser) {
                UserModel userModel = ConvertUserModel(temp);
                if (userModel != null) {
                    listusermodel.add(userModel);
                }
            }
            resultListModel.setRows(listusermodel);
        }
        resultListModel.setTotal(count);
        resultListModel.setResult(true);
        return resultListModel;
    }

    /**
     * [功能描述]：Ajax插入User数据
     */
    @RequestMapping(value = "/insertUsers", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertUsers(UserModel usermodel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (usermodel != null) {
            try {
                String userMaxEnc = sysflagService.getSysFlagValue("usermax");
                if (!StringUtils.isEmpty(userMaxEnc)) {
                    String userMaxDec = DesUtil.Decrypt(userMaxEnc, "xpm84228");
                    if (!StringUtils.isEmpty(userMaxDec)) {
                        int sysUserCount = userService.getCount(null, true);
                        if (sysUserCount >= Double.valueOf(userMaxDec)) {
                            resultModel.setResult(false);
                            resultModel.setDetail("不能新增用户：系统用户已达上限！");
                            return resultModel;
                        }
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("不能新增用户：未设置系统用户数！");
                    return resultModel;
                }
                User user = new User();
                user.setUserCode(usermodel.getUserCode());
                if (userService.getCount(user, true) == 0) {
                    List<User> listuser = new ArrayList<User>();
                    usermodel.setUserPassword(DefaultArgument.PWD_DEFAULT);
                    listuser.add(ConvertUser(usermodel, true, httpsession));
                    int intresult = userService.insertUser(listuser);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增用户失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此用户，请使用其他登录账号！");
                }

            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增用户失败！");
                logger.error(LOG + "：新增用户失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * [功能描述]：修改用户信息
     */
    @RequestMapping(value = "/updatetUsers", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateUsers(UserModel usermodel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (usermodel != null) {
            try {
                if (userService.getUserExist(usermodel.getUserId(),
                        usermodel.getUserCode()) == 0) {
                    List<User> listuser = new ArrayList<User>();
                    listuser.add(ConvertUser(usermodel, true, httpsession));
                    int intresult = userService.updateUser(listuser);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新用户失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此用户，请使用其他登录账号！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新用户失败！");
                logger.error(LOG + "：更新用户失败，信息为：" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * [功能描述]：重置密码
     */
    @RequestMapping(value = "/resetUsersPwd", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel resetUsersPwd(UserModel usermodel) {
        ResultModel resultModel = new ResultModel();
        if (usermodel != null) {
            try {
                int intresult = userService.updateUserPwd(
                        usermodel.getUserId(),
                        SHAUtil.shaEncode(DefaultArgument.PWD_DEFAULT));
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新用户失败！");
                }

            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新用户失败！");
                logger.error(LOG + "：更新用户失败，信息为：" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * [功能描述]：修改密码
     */
    @RequestMapping(value = "/updateUsersPwd", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateUsersPwd(String oldPwd, String newPwd, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (!newPwd.isEmpty() && !oldPwd.isEmpty()) {

            try {
                newPwd = SHAUtil.shaEncode(newPwd);
                oldPwd = SHAUtil.shaEncode(oldPwd);
                UserModel loginuser = (UserModel) httpsession
                        .getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser == null) {
                    resultModel.setResult(false);
                    resultModel.setDetail("未登录，不能进行密码修改！");
                } else if (!loginuser.getUserPassword().equals(oldPwd)) {
                    resultModel.setResult(false);
                    resultModel.setDetail("密码不正确！");
                } else if (loginuser.getUserPassword().equals(newPwd)) {
                    resultModel.setResult(false);
                    resultModel.setDetail("新密码与原密码一致！");
                } else {
                    int intresult = userService.updateUserPwd(
                            loginuser.getUserId(), newPwd);
                    if (intresult > 0) {
                        ((UserModel) httpsession
                                .getAttribute(DefaultArgument.LOGIN_USER))
                                .setUserPassword(newPwd);
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新用户失败！");
                    }
                }

            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新用户失败！");
                logger.error(LOG + "：更新用户失败，信息为：" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * [功能描述]：删除用户
     */
    @RequestMapping(value = "/deleteUsers", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteUsers(@RequestParam(value = "list[]") List<String> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<String> listid = new ArrayList<String>();
                for (String userid : list) {
                    String userCode = userService.getUserCodeById(userid);
                    if (!dom4jConfig.getDeDevConfig().getRootUser().equals(userCode)) {
                        listid.add(userid);
                    }
                }
                int intresult = userService.deleteUser(listid);
                if (intresult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("删除用户成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除用户失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除用户失败！");
                logger.error(LOG + "：删除用户失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除用户失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * [功能描述]：更新用户删除标识
     */
    @RequestMapping(value = "/updateUserDelete", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateUserDelete(String userId, String userDelete) {
        ResultModel resultModel = new ResultModel();
        if (userId != null && !userId.isEmpty() && userDelete != null && !userDelete.isEmpty()) {
            try {
                String userCode = userService.getUserCodeById(userId);
                if (dom4jConfig.getDeDevConfig().getRootUser().equals(userCode)) {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统用户不能执行此操作，请联系管理员！");
                } else {
                    int intresult = userService.updateUserDelete(userId, userDelete);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("用户状态设置成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("用户状态设置失败！");
                    }
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("用户状态设置失败！");
                logger.error(LOG + "：用户状态设置失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("用户状态设置失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * [功能描述]：将UserModel转换成User
     */
    private User ConvertUser(UserModel userModel, boolean encryFlag, HttpSession httpsession) {
        User user = new User();
        if (userModel != null) {
            try {
                user.setUserId(userModel.getUserId());
                user.setUserCode(userModel.getUserCode());
                user.setUserName(userModel.getUserName());
                if (userModel.getUserPassword() != null) {
                    if (encryFlag) {
                        user.setUserPassword(SHAUtil.shaEncode(userModel.getUserPassword()));
                    } else {
                        user.setUserPassword(userModel.getUserPassword());
                    }
                }
                user.setStatisticsTime(userModel.getStatisticsTime());
                user.setUserTel(userModel.getUserTel());
                user.setUserEmail(userModel.getUserEmail());
                user.setUserDelete(userModel.getUserDelete());
                user.setUserRemark(userModel.getUserRemark());
                Oranization oranization = new Oranization();
                oranization.setOrgId(userModel.getOrgId());
                user.setOranization(oranization);
                UserModel loginuser = (UserModel) httpsession
                        .getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    user.setOptUser(loginuser.getUserId());
                } else {
                    user.setOptUser(userModel.getUserId());
                }
                user.setRowCount(userModel.getRows());
                user.setRowIndex((userModel.getPage() - 1)
                        * userModel.getRows());
            } catch (Exception e) {
                logger.error(LOG + "：将UserModel转换成User失败，信息为" + e.getMessage());
            }
        }
        return user;
    }

    /**
     * [功能描述]：将User转换成UserModel
     */
    public UserModel ConvertUserModel(User user) {
        UserModel userModel = new UserModel();
        if (user != null) {
            userModel.setUserId(user.getUserId());
            userModel.setUserCode(user.getUserCode());
            userModel.setUserName(user.getUserName());
            userModel.setOrgId(user.getOranization().getOrgId());
            userModel.setOrgName(user.getOranization().getOrgName());
            userModel.setStatisticsTime(user.getStatisticsTime());
            userModel.setUserTel(user.getUserTel());
            userModel.setUserEmail(user.getUserEmail());
            userModel.setUserDelete(user.getUserDelete());
            if (user.getUserDelete()) {
                userModel.setUserDeleteName("停用");
            } else {
                userModel.setUserDeleteName("正常");
            }
            userModel.setUserRemark(user.getUserRemark());
            userModel.setOptUserName(userService.getUserNameById(
                    user.getOptUser(), null));
            userModel.setOptTime(DateUtil.TimestampToString(user.getOptTime(),
                    DateUtil.DATA_TIME));
        }
        return userModel;
    }

    /**
     * [功能描述]：退出系统
     */
    @RequestMapping(value = "/toLogout", method = {RequestMethod.POST,
            RequestMethod.GET})
    private @ResponseBody
    ResultModel toLogout(HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        /* 剔除websocket中该Session的Id */
        String sessionId = httpsession.getId();
        websocketHandler.removeSessionId(sessionId);
        httpsession.removeAttribute(DefaultArgument.LOGIN_USER);
        httpsession.invalidate();
        resultModel.setResult(true);
        resultModel.setDetail("退出系统成功！");
        return resultModel;
    }

}
