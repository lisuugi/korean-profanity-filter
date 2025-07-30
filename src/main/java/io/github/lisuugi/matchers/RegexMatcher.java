package io.github.lisuugi.matchers;

import io.github.lisuugi.dto.FoundWord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher implements MatchingStrategy {

    // [성능 최적화] 정규식 패턴은 애플리케이션 시작 시 한 번만 컴파일하여 재사용합니다.
    // 이렇게 하면 매번 필터링할 때마다 정규식을 컴파일하는 비용을 없앨 수 있습니다.
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            "[시씨씪슈쓔쉬쉽쒸쓉](?:[0-9]*|[0-9]+ *)[바발벌빠빡빨뻘파팔펄]|[섊좆좇졷좄좃좉졽썅춍봊]|[ㅈ조][0-9]*까|ㅅㅣㅂㅏㄹ?|ㅂ[0-9]*ㅅ|[ㅄᄲᇪᄺᄡᄣᄦᇠ]|[ㅅㅆᄴ][0-9]*[ㄲㅅㅆᄴㅂ]|[존좉좇][0-9 ]*나|[자보][0-9]+지|보빨|[봊봋봇봈볻봁봍] *[빨이]|[후훚훐훛훋훗훘훟훝훑][장앙]|[엠앰]창|애[미비]|애자|[가-탏탑-힣]색기|(?:[샊샛세쉐쉑쉨쉒객갞갟갯갰갴겍겎겏겤곅곆곇곗곘곜걕걖걗걧걨걬] *[끼키퀴])|새 *[키퀴]|[병븅][0-9]*[신딱딲]|미친[가-닣닥-힣]|[믿밑]힌|[염옘][0-9]*병|[샊샛샜샠섹섺셋셌셐셱솃솄솈섁섂섓섔섘]기|[섹섺섻쎅쎆쎇쎽쎾쎿섁섂섃썍썎썏][스쓰]|[지야][0-9]*랄|니[애에]미|갈[0-9]*보[^가-힣]|[뻐뻑뻒뻙뻨][0-9]*[뀨큐킹낑]|꼬[0-9]*추|곧[0-9]*휴|[가-힣]슬아치|자[0-9]*박꼼|빨통|[사싸](?:이코|가지|[0-9]*까시)|육[0-9]*시[랄럴]|육[0-9]*실[알얼할헐]|즐[^가-힣]|찌[0-9]*(?:질이|랭이)|찐[0-9]*따|찐[0-9]*찌버거|창[녀놈]|[가-힣]{2,}충[^가-힣]|[가-힣]{2,}츙|부녀자|화냥년|환[양향]년|호[0-9]*[구모]|조[선센][징]|조센|[쪼쪽쪾](?:[발빨]이|[바빠]리)|盧|무현|찌끄[레래]기|(?:하악){2,}|하[앍앜]|[낭당랑앙항남담람암함][ ]?[가-힣]+[띠찌]|느[금급]마|文在|在寅|(?<=[^\\n])[家哥]|속냐|[tT]l[qQ]kf|Wls|[ㅂ]신|[ㅅ]발|[ㅈ]밥"
    );

    /**
     * 텍스트에 정규식과 일치하는 부분이 하나라도 있는지 확인합니다.
     *
     * @param text 검사할 텍스트
     * @return 일치하는 부분이 있으면 true, 없으면 false
     */
    @Override
    public boolean contains(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return REGEX_PATTERN.matcher(text).find();
    }

//    /**
//     * 텍스트에서 정규식과 일치하는 모든 부분을 찾아 FoundTerm 객체 컬렉션으로 반환합니다.
//     *
//     * @param text 검사할 텍스트
//     * @return 찾은 모든 단어 정보가 담긴 Collection
//     */
//    @Override
//    public Collection<FoundWord> findWords(String text) {
//        if (text == null || text.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        List<FoundWord> foundTerms = new ArrayList<>();
//        Matcher matcher = REGEX_PATTERN.matcher(text);
//
//        // find() 메서드를 반복 호출하여 텍스트 내의 모든 일치 항목을 순회합니다.
//        while (matcher.find()) {
//            // 일치하는 부분을 찾을 때마다 FoundTerm 객체를 생성합니다.
//            foundTerms.add(new FoundWord(
//                    matcher.group(), // 일치한 실제 문자열
//                    matcher.start(), // 시작 인덱스
//                    matcher.end()    // 끝 인덱스 (exclusive)
//            ));
//        }
//
//        return foundTerms;
//    }
}