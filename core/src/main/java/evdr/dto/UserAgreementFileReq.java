package evdr.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserAgreementFileReq
{

    /*
    DR동의서 파일
    multipart 타입의 파일객체.
        파일명 길이는 최대 100
        파일갯수는 최대 10개
        파일1개 크기는 최대 200KB (연동시 조정 가능)
        파일명 규칙 :
            {동의일}-{customerNo}-kepco-{kepcoCustomerNo}-{국민/플러스DR}.pdf
            국민DR:1, 플러스DR:2
     */
    public List<MultipartFile> agreementFiles;
    /*
    DR동의서 파일명
    업로드하는 파일들의 파일명 리스트
     */
    public List<String> fileInfos;

    public UserAgreementFileReq(List<MultipartFile> agreementFiles, List<String> fileInfos) {
        this.agreementFiles = agreementFiles;
        this.fileInfos = fileInfos;
    }
}
