package com.tianshouzhi.oauth.controller;

/**
 * Created by tianshouzhi on 2018/3/7.
 */
@RestController
public class ResourceController {
    private final static Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private final static String DEFAULT_NAME = "xuan";
    private static String DEFAULT_SERVICE_ID = "application";
    private static String DEFAULT_HOST = "localhost";
    private static int DEFAULT_PORT = 8080;


    // 受保护资源
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable("userId") String userId){
        logger.info("Get User by UserId {}", userId );
        return new User(userId, DEFAULT_NAME);
    }

    // 不保护资源
    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceId(@PathVariable("serviceId") String serviceId){
        logger.info("Get Instance by serviceId {}", serviceId);
        return new Instance(serviceId, DEFAULT_HOST, DEFAULT_PORT);
    }
}
