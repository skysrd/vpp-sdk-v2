package dr;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import evdr.EVDRService;
import evdr.dto.CustomerRegisterInfoReq;
import evdr.dto.CustomerRegisterInfoResponse;
import evdr.dto.DRServiceResponse;
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
    public evdr.EVDRService EVDRService = new EVDRService(ServiceType.TB, clientInfo);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EVDRServiceTest() throws Exception {
    }

    @Test
    void bulkPowerConsumptionData() {
        //given

    }

    @Test
    void powerConsumptionData() {
        //given
    }

    @Test
    void customerRegisterInfo() throws Exception {
        //given
        CustomerRegisterInfoReq.Resource resource1 = new CustomerRegisterInfoReq.Resource(
                "00001", "goldtroops", "20240924122200", "50", "110", "126", "00", "테스트 주소 1", (short) 1, "00001", "01", "01", (short) 5);
        CustomerRegisterInfoReq.Resource resource2 = new CustomerRegisterInfoReq.Resource(
                "00002", "goldtroops", "20240924122200", "50", "110", "126", "00", "테스트 주소 2", (short) 1, "00002", "01", "01", (short) 5);

        CustomerRegisterInfoReq.Item item1 = new CustomerRegisterInfoReq.Item(
                "g0219059275", (short) 1, "임관선", "01055999690", List.of(resource1, resource2));

        CustomerRegisterInfoReq request = new CustomerRegisterInfoReq(List.of(item1));

        //when
        CustomerRegisterInfoResponse response = EVDRService.customerRegisterInfo(request);

        //then
        assertThat(response).isNotNull();
        System.out.println("Response : " + objectMapper.writeValueAsString(response));
    }

    @Test
    void customerUnregisterInfo() {
    }

    @Test
    void participationResult() {
    }

    @Test
    void userAgreementFile_Success() throws Exception {
        //given
        File file1 = new File("/resources/20230406-1000001-kepco-10221020320123034-1.pdf");
        File file2 = new File("/resources/20230406-1000001-kepco-10221020320123034-2.pdf");
        File file3 = new File("/resources/20230406-1000001-kepco-10221020320123035-1.pdf");
        File file4 = new File("/resources/20230406-2000001-kepco-20221020320123034-1.pdf");

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
        DRServiceResponse response = EVDRService.userAgreementFile(request);

        //then
        assertThat(response).isNotNull();
        System.out.println("Response : " + objectMapper.writeValueAsString(response));
    }
}