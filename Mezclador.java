public class Mezclador {
    public String mezcla(String textoA, String textoB){
        int cada = ((int) textoB.length()/2)+1;
        int hasta = textoA.length();
        char aux1[] = textoA.toCharArray();
        char aux2[] = textoB.toCharArray();
        int j = 0;
        for (int i = 0; i < hasta; i++) {
            if (cada % (i+1) == 0) {
                aux1[i] = aux2[j];
                j = j+1;
            }
        }
        String mezclado = new String(aux1);
        return mezclado;
    }
}
