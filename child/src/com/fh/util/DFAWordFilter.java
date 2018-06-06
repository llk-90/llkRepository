package com.fh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by 860115007 on 2016/7/11.
 */
public class DFAWordFilter {
    //字符编码
    public static final String ENCODING = "UTF-8";

    private Map sensitiveWordMap = null;

    // 最小匹配规则
    public static int MIN_MATCH_TYPE = 1;

    // 最大匹配规则
    public static int MAX_MATCH_TYPE = 2;

    // 单例
    private static DFAWordFilter inst = null;

    /**
     * 构造函数，初始化敏感词库
     */
    private DFAWordFilter() {
        sensitiveWordMap = LoadWord.INSTANCE.getKeywordMap();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static DFAWordFilter getInstance() {
        if (null == inst) {
            inst = new DFAWordFilter();
        }
        return inst;
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt
     * @param matchType
     * @return
     */
    public boolean isContaintSensitiveWord(String txt, int matchType) {
        boolean flag = false;
        int txtLen = txt.length();
        for (int i = 0; i < txtLen; i++) {
            // 判断是否包含敏感字符
            int matchFlag = this.CheckSensitiveWord(txt, i, matchType);
            // 大于0存在，返回true
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt
     * @param matchType
     * @return
     */
    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<String>();

        int txtLen = txt.length();
        for (int i = 0; i < txtLen; i++) {

            // 判断是否包含敏感字符
            int length = CheckSensitiveWord(txt, i, matchType);

            // 存在,加入list中
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));

                // 减1的原因，是因为for会自增
                i = i + length - 1;
            }
        }

        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt
     * @param matchType
     * @param replaceChar
     * @return
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {

        String resultTxt = txt;

        // 获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar
     * @param length
     * @return
     */
    private String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * 如果存在，则返回敏感词字符的长度，不存在返回0
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return
     */
    public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {

        // 敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;

        // 匹配标识数默认为0
        int matchFlag = 0;
        Map nowMap = sensitiveWordMap;
        int txtLen = txt.length();
        for (int i = beginIndex; i < txtLen; i++) {
            char word = txt.charAt(i);

            // 获取指定key
            nowMap = (Map) nowMap.get(word);

            // 存在，则判断是否为最后一个
            if (nowMap != null) {

                // 找到相应key，匹配标识+1
                matchFlag++;

                // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {

                    // 结束标志位为true
                    flag = true;

                    // 最小规则，直接返回,最大规则还需继续查找
                    if (DFAWordFilter.MIN_MATCH_TYPE == matchType) {
                        break;
                    }
                }
            }

            // 不存在，直接返回
            else {
                break;
            }
        }

        // 长度必须大于等于1，为词
        if (matchFlag < 2 || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }
    public static String filter(String str){
    	DFAWordFilter filter = DFAWordFilter.getInstance();
    	return filter.replaceSensitiveWord(str, DFAWordFilter.MAX_MATCH_TYPE, "*");
    }

    public static void main(String[] args) {
        DFAWordFilter filter = DFAWordFilter.getInstance();
        String before = "中新社北京7月9日电 (记者 梁晓辉)全面深化改革，是决定当代中国命运的“关键一招”；创新发展，是引领中国发展的第一动力。在“十三五”规划开局之年进入下半程时，回顾中共中央总书记、国家主席习近平2016年上半年的调研路径和系列讲话，人们可以清晰看到改革和创新这两条贯穿始终的主线。\n" +
                "“地方是推进改革的重要力量”。在中央深改组第二十五次会议上，习近平如是强调地方在改革中的作用。事实上，对地方改革的重视，一直贯穿于习近平的国内调研路径。今年上半年，习近平先后去到重庆、安徽、黑龙江调研考察，并各有侧重地强调落实改革任务。\n" +
                "在经济增速亮眼的重庆，习近平强调，要加大供给侧结构性改革力度，重点是促进产能过剩有效化解，促进产业优化重组，降低企业成本，发展战略性新兴产业和现代服务业，增加公共产品和服务供给，增强经济持续增长动力。\n" +
                "在农村改革的发源地安徽小岗村，习近平强调，解决农业农村发展面临的各种矛盾和问题，根本靠深化改革。最大的政策，就是必须坚持和完善农村基本经营制度，坚持农村土地集体所有，坚持家庭经营基础性地位，坚持稳定土地承包关系。\n" +
                "在经济转型压力较大的黑龙江，习近平强调，要深化改革开放优化发展环境，闯出老工业基地振兴发展新路。黑龙江转方式调结构任务艰巨，要着力优化产业结构，改造升级“老字号”，深度开发“原字号”，培育壮大“新字号”。\n" +
                "中共中央党校教授戴焰军认为，研究和推进全面深化改革，是习近平治国理政贯穿始终的主题之一。习近平的调研路径，既涵盖中国经济发展较快的地区，也关照经济转型压力较大的地区，更去到具有改革象征意义的地方，“是一条以改革为主线的调研之路，传递出改革开放矢志不渝，攻坚克难决不止步的鲜明信号”。\n" +
                "创新发展，是位于中国五大发展理念之首的关键要素，“也是贯穿习近平国内调研的另一条主线”，戴焰军说。\n" +
                "在重庆，习近平指出，地方抓改革、推改革，一方面要落实好党中央部署的改革任务，一方面要搞好探索创新。\n" +
                "在安徽，习近平强调，广大知识分子要增强创新意识，把握创新特点，遵循创新规律，既奇思妙想、“无中生有”，又兼收并蓄、博采众长，甘于“十年磨一剑”，在尊重个人创造的同时注重发挥集体攻关的优势。\n" +
                "在黑龙江，习近平亦指出，要加强创新能力建设，强化创新链和产业链、创新链和服务链、创新链和资金链对接，把振兴发展的基点放在创新上。\n" +
                "调研之外，在多个场合的讲话中，习近平亦强调大力创新。\n" +
                "不创新不行，创新慢了也不行。在全国科技创新大会上，习近平指出创新的重要性：如果我们不识变、不应变、不求变，就可能陷入战略被动，错失发展机遇，甚至错过整整一个时代。\n" +
                "在哲学社会科学工作座谈会上，习近平指出，理论的生命力在于创新，创新是哲学社会科学发展的永恒主题。“如果不能及时研究、提出、运用新思想、新理念、新办法，理论就会苍白无力，哲学社会科学就会‘肌无力’。”\n" +
                "在党的舆论工作座谈会上，习近平指出，必须创新理念、内容、体裁、形式、方法、手段、业态、体制、机制，增强针对性和实效性。\n" +
                "“创新从根本上说，就是要做到实事求是，跟上时代和外部环境的变化”，戴焰军表示。他认为，习近平在调研和系列讲话中几乎方方面面都讲创新，既从正面讲创新的好处，也警醒世人不创新就跟不上发展的要求。“是把创新摆在国家发展全局的核心位置，让创新贯穿党和国家一切工作的体现。”(完)";
        long start = System.currentTimeMillis();
        String after = filter.replaceSensitiveWord(before, DFAWordFilter.MAX_MATCH_TYPE, "*");
        System.out.println("耗时: " + (System.currentTimeMillis() - start) + " 毫秒");
        System.out.println("替换前的文字为：\n" + before);
        System.out.println();
        System.out.println("替换后的文字为：\n" + after);
    }


    /**
     * 加载敏感词汇
     */
    public enum LoadWord {

        INSTANCE;
        private Map keywordMap;

        public Map getKeywordMap() {
            return keywordMap;
        }

        LoadWord() {
            keywordMap = initKeyWord();
//            System.out.println(keywordMap);
//            System.out.println(keywordMap.size());
        }

        private Map initKeyWord() {
            // 读取敏感词库
            Set<String> wordSet = readSensitiveWordFile();
            // 将敏感词库加入到HashMap中
            return addSensitiveWordToHashMap(wordSet);
        }//end method initKeyWord

        private Map addSensitiveWordToHashMap(Set<String> wordSet) {
            // 初始化敏感词容器，减少扩容操作
            Map wordMap = new HashMap(wordSet.size());
            for (String word : wordSet) {
                Map nowMap = wordMap;
                int wordLen = word.length();
                for (int i = 0; i < wordLen; i++) {
                    // 转换成char型
                    char keyChar = word.charAt(i);
                    // 获取
                    Object tempMap = nowMap.get(keyChar);
                    if (tempMap != null) {// 如果存在该key，直接赋值
                        nowMap = (Map) tempMap;
                    } else {// 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                        // 设置标志位
                        Map newMap = new HashMap();
                        newMap.put("isEnd", "0");
                        // 添加到集合
                        nowMap.put(keyChar, newMap);
                        nowMap = newMap;
                    }
                    // 最后一个
                    if (i == word.length() - 1) {
                        nowMap.put("isEnd", "1");
                    }
                }
            }
            return wordMap;
        }//end method addSensitiveWordToHashMap

        private Set<String> readSensitiveWordFile() {
            Set<String> wordSet = null;
            ClassLoader classLoader = getClass().getClassLoader();
            /*File file = new File(classLoader.getResource("keyword3.txt").getFile());*/
            /*String path = DFAWordFilter.class.getClassLoader().getResource("keyword3.txt").getPath(); 
            File file = new File(path);*/
            //File file = new File("G:/a/keyword.txt");
            File file = new File(DFAWordFilter.class.getClassLoader().getResource("keyword.txt").getFile());
            
            System.out.println("读取敏感词汇文件.........."+DFAWordFilter.class.getClassLoader().getResource("keyword.txt"));
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
                System.out.println("=====================isfile=======================");
                // 文件流是否存在
                if (file.isFile() && file.exists()) {
                	System.out.println("=============================if===================");
                    wordSet = new HashSet<String>();
                    StringBuffer sb = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String txt = null;
                    // 读取文件，将文件内容放入到set中
                    String splitType = bufferedReader.readLine();
                    while ((txt = bufferedReader.readLine()) != null) {
                        if (splitType.equals("回车")) {
                            if (splitType.trim().length() == 0) continue;
                            wordSet.add(txt);
                        } else if (splitType.equals("分隔符")) {
                            sb.append(txt);
                        } else {
                            throw new Exception("程序猿大哥，请在敏感字库第一行，写上分割方式，例如：回车 或 分割符。分割符只分割中英文逗号");
                        }
                    }
                    bufferedReader.close();
                    if (splitType.equals("分割符")) {
                        String str = sb.toString();
                        String[] ss = str.split("[，,\n]");//匹配中英文逗号
                        for (String s : ss) {
                            wordSet.add(s);
                        }
                    }
                }
                // 关闭文件流
                read.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return wordSet;
        }//end method readSensitiveWordFile
    }//end inner class
}//end class
