package dr;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import evdr.EVDRService;
import evdr.dto.BulkPowerConsumptionDataReq;
import evdr.dto.BulkPowerConsumptionDataResponse;
import evdr.dto.CustomerRegisterInfoReq;
import evdr.dto.CustomerRegisterInfoResponse;
import evdr.dto.DRServiceResponse;
import evdr.dto.ParticipationResultReq;
import evdr.dto.ParticipationResultResponse;
import evdr.dto.UserAgreementFileReq;
import common.ServiceType;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EVDRServiceTest {
    public ClientInfo clientInfo = new ClientInfo("herit", "ffe0356c-10c2-409c-8342-6d5328b90968");
    public evdr.EVDRService evdrService = new EVDRService(ServiceType.TB, clientInfo);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EVDRServiceTest() throws Exception {
    }

    @Test
    void bulkPowerConsumptionData() throws Exception {
        //given
//        String jsonString = "{\"items\":[{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240911\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240912\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240913\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240914\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240915\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240916\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240917\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240918\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240919\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240920\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240921\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240922\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240923\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240924\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20240925\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]}]}";

//        String jsonString = "{\"items\":[{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240911\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240912\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240913\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240914\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240915\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240916\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240917\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240918\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240919\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240920\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240921\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240922\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240923\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240924\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]},{\"resourceId\":\"023456786\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456786\",\"date\":\"20240925\",\"data\":[[\"0000\",1100],[\"0015\",1100],[\"0030\",1200]]}]}";

        String jsonString = "{\"items\":[{\"resourceId\":\"023456785\",\"resourceIssuer\":\"goldtroops\",\"meterType\":1,\"meterNo\":\"023456785\",\"date\":\"20241031\",\"data\":[[\"1400\",50],[\"1415\",100],[\"1430\",100],[\"1445\",50]]}]}";



        BulkPowerConsumptionDataReq req = objectMapper.readValue(jsonString, BulkPowerConsumptionDataReq.class);

        //when
        BulkPowerConsumptionDataResponse response = evdrService.bulkPowerConsumptionData(req);
        System.out.println("Response : " + objectMapper.writeValueAsString(response));

        //then
        assertThat(response).isNotNull();
        assertThat(response.result.code).isEqualTo("0");
    }

    @Test
    void powerConsumptionData() {
        //given


        //when


        //then



    }

    @Test
    void customerRegisterInfo() throws Exception {
        //given
        CustomerRegisterInfoReq.Resource resource1 = new CustomerRegisterInfoReq.Resource(
                "012345013", "kepco", "20241029122200", "46", "110", "126", "00", "테스트 주소 1", (short) 1, "012345013", "01", "01", (short) 1);
        CustomerRegisterInfoReq.Item item1 = new CustomerRegisterInfoReq.Item(
                "004", (short) 1, "프랜차이즈_국민DR_테스트2", "01012341234", List.of(resource1));
/*
        CustomerRegisterInfoReq.Resource resource1 = new CustomerRegisterInfoReq.Resource(
                "023456786", "goldtroops", "20241029122200", "43", "110", "126", "00", "테스트 주소 1", (short) 1, "023456785", "01", "01", (short) 5);
        CustomerRegisterInfoReq.Item item1 = new CustomerRegisterInfoReq.Item(
                "plusdsdktest3", (short) 1, "PlusDRTest2", "01012341234", List.of(resource1));*/

        CustomerRegisterInfoReq request = new CustomerRegisterInfoReq(List.of(item1));

        //when
        CustomerRegisterInfoResponse response = evdrService.customerRegisterInfo(request);

        //then
        assertThat(response).isNotNull();
        System.out.println("Response : " + objectMapper.writeValueAsString(response));
    }

    @Test
    void participationResult() throws Exception {
        //given
        ParticipationResultReq.Item item1 = new ParticipationResultReq.Item("023456785", "goldtroops", 100, 100);

        ParticipationResultReq request = new ParticipationResultReq("62134921202410311400", 100, 100, List.of(item1));

        //when
        ParticipationResultResponse drServiceResponse = evdrService.participationResult(request);
        System.out.println("Response : " + objectMapper.writeValueAsString(drServiceResponse));

        //then
        assertThat(drServiceResponse).isNotNull();
        assertThat(drServiceResponse.result.code).isEqualTo("0");
    }

    @Test
    void userAgreementFile_Success() throws Exception {
        //given
        File file1 = new File("/resources/20241106-003-kepco-012345601-1.pdf");
        File file2 = new File("/resources/20241106-003-kepco-012345601-2.pdf");
        File file3 = new File("/resources/20241106-003-kepco-012345601-3.pdf");
        File file4 = new File("/resources/20241106-003-kepco-012345601-4.pdf");

        /*
        File file1 = new File("/resources/20241030-005-goldtroops-023456786-1.pdf");
        File file2 = new File("/resources/20241030-005-goldtroops-023456786-2.pdf");
        File file3 = new File("/resources/20241030-005-goldtroops-023456786-3.pdf");
        File file4 = new File("/resources/20241030-005-goldtroops-023456786-4.pdf");*/

        List<File> fileList = List.of(file1, file2, file3, file4);
        List<MultipartFile> multipartFiles = new ArrayList<>();

        for (File file : fileList) {
            Path path = file.toPath();
            String name = file.getName();
            String originalFileName = file.getName();
            String contentType = Files.probeContentType(path);
            byte[] content = null;
            try {
                content = Files.readAllBytes(path);
            } catch (final IOException e) {
            }
            MultipartFile multipartFile = new MockMultipartFile(name,
                    originalFileName, contentType, content);
            multipartFiles.add(multipartFile);
        }

        UserAgreementFileReq request = new UserAgreementFileReq();
        request.agreementFiles = multipartFiles;
        request.fileInfos = List.of(file1.getName(), file2.getName(), file3.getName(), file4.getName());

        //when
        DRServiceResponse response = evdrService.userAgreementFile(request);

        //then
        assertThat(response).isNotNull();
        System.out.println("Response : " + objectMapper.writeValueAsString(response));
    }
}