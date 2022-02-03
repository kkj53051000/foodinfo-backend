//package com.kp.foodinfo.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kp.foodinfo.dto.FileTestUtilControllerDto;
//import com.kp.foodinfo.service.CollabPlatformService;
//import com.kp.foodinfo.util.FileTestUtil;
//import com.kp.foodinfo.vo.BasicVo;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CollabPlatformControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    @Transactional
//    public void COLLAB_PLATFORM_PROCESS_TEST() throws Exception {
//        //BrandEventService Change Mock
//        CollabPlatformService collabPlatformService = Mockito.mock(CollabPlatformService.class);
//        CollabPlatformController collabPlatformController = new CollabPlatformController(collabPlatformService);
//        mockMvc = MockMvcBuilders.standaloneSetup(collabPlatformController).build();
//
//        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();
//
//        BasicVo basicVo = new BasicVo("success");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/collabplatformprocess")
//                .file(fileRequest.getFile())
//                .requestAttr("request", fileRequest.getRequest())
//                .param("name", "test"))
//                .andExpect(content().string(jsonBasicVo))
//                .andDo(print());
//    }
//}