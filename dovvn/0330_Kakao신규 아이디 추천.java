class Solution {
    public String solution(String new_id){
       String s = "";
        //1,2,3,4
        s = new_id.toLowerCase().replaceAll("[^a-z0-9._-]","").replaceAll("[.]{2,}", ".").replaceAll("^\\.|\\.$","");

        if(s.length()==0) s = "a"; //5
        if(s.length() >= 16) s = s.substring(0, 15).replaceAll("\\.$", ""); //6

        //7
        StringBuilder sb = new StringBuilder();
        if(s.length()<=2){
            sb.append(s);
            char c = s.charAt(s.length()-1);
            while(sb.length()<3) sb.append(c);
            return sb.toString();
        }
        return s;
    }
}