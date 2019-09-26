package com.aisino.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import sun.misc.BASE64Decoder;

public class TestEWM {
    public static void main(String[] args) {
        BASE64Decoder base64s = new BASE64Decoder();
//        String ewm="iVBORw0KGgoAAAANSUhEUgAAAIsAAACLCAIAAADee/2oAAADtklEQVR42u3bUXasIBBFUec/aTOApJdQ91Sp6eNnnvEB2yVQlxyn17OvwyF4h9CRXX8899c/rdy89V+sPKc4KDv9WulyMqoKKaQQK0T1ZKWVoVB4z9Y7hHOuP1AhhRRqEtoa2dqIUPNHbYYLp0x8ZlJIIYVeKhSupMOXYOvXa/MiNT4KKaTQ24WoFTm1Y++rDiikkEKvEKotc/s26rUnh+vmvm3AbVUfhRT6eqHwU+5Pnp7g+ROFvlUIO5hCp9e1UmyYYE2mQatnfRRSSKFMiNpFh4WDvq88vhSmKrCXzVBIIYXmhcJjN+ExoLASQb0EY/ULhRRSCBEKRy383FPnb/A21+4Jay4X+yGFFFKoJBTmOlSyEg4xtbbGyZO3UyGFFGoSCteyYe0yjKjD7vRVIgqDqZBCCo0JUVMU1ROqElGbDgemOoUUUggU6ss/+mDCgcBrJU0Tm0IKKYQIhWXEcKcdSvcF5LeEZAoppFCrEL6ZDz/l1Dw0mUVR5RiFFFIIFKLqgNQ6dbI4S9VkmxI1hRRSCBEK98PhmRhqAU3h4a9pUslVSCGFEKEw6cFTHJwBr2j0BVf1lYJCCin0WeigrzO7wq98eHgoDMjDZqxWfRRSSKFbM9YwQ0L6dnJ/TkwVQwsvikIKKcQKUadbkAAYTKfweYhqT32loJBCCu0IUYVFajuNz3kDcRcOrJBCCnUI4Zt5vNt4M/C4qzcfUkghhWihWxpHDR9VUwiz/OQehRRSCBHCwxLq1A5ejqTiLmrGveypQgophAhRMwo1AYQvwWShk2rhp+copJBCrFDf2RpqQY+XYmvbgDDvX2+PQgopNC9EdTKcovqGL4z5w8MBqwmeQgoptCM0UBSgqgNUJRc/9NP0eimkkEKIEF4MxRnO0oXnQ+G2pDAaCimkECJEZcz4ljskx4sUfbnX6kpBIYUUKglR52bCPKavyhDOQ2EWlbxMCimkUJNQuEoOf4tamuOlBCpVWn/vFVJIIVYI3xiHH25qjY6HQH1VWuA0lkIKKVTKh6jwhup2H1W406AORSmkkEKgEHXV+oakxeX2UEXVsPFATUEhhRTayYf6wt1bIp+wvfkFVTwqVXIUUUogVChMaCo/qbS3dr02reH6mkEIKtQpRB1aoU0QDJdS+nUayCVFIIYX+gRBerqXydarckMymCimk0FuE7i19UmvigTlYIYUUmhGi4pxaZB7eTJVQqReuNq0qpJBCHUL4SRrkW5zb95V9Q9fLJyukkEKIkNdjL4Wefv0AeVHfqRlqooMAAAAASUVORK5CYII=";
//        String ewm="Qk3CAwAAAAAAAD4AAAAoAAAASwAAAEsAAAABAAEAAAAAAIQDAAAAAAAAAAAAAAAAAAACAAAAAAAA///////////////////gAAAAAz888/PwD/8gAAAAAz888/PwD/8gAAA/888DADz8w/AgAAA/888DADz8w/AgAAAwM8AMADww/w8gAAAwM8AMADww/w8gAAAwMzAzM//wMMMgAAAwMzAzM//wMMMgAAAwMwzP/MA8AAzgAAAwMwzP/MA8AAzgAAA/8zP/wPMPPz8gAAA/8zP/wPMPPz8gAAAAAzw8PMDPMwAgAAAAAzw8PMDPMwAgAAD//zzMPP8wPzPgAAD//zzMPP8wPzPgAADPMAzzMzzAADAgAADPMAzzMzzAADAgAADMDzAA/zzwwzDgAADMDzAA/zzwwzDgAADwwwwDPAMMMw8gAADwwwwDPAMMMw8gAADz/MwPPDwMzAzgAADz/MwPPDwMzAzgAAAM8wAwA8/ww/8gAAAM8wAwA8/ww/8gAADPPAP/DAAzM/DgAADPPAP/DAAzM/DgAADPMA/8AzDAADwgAADPMA/8AzDAADwgAAAMP/DAPAw8DzDgAAAMP/DAPAw8DzDgAAAPMPwAwzP88z8gAAAPMPwAwzP88z8gAADAD/wD//8AMz8gAADAD/wD//8AMz8gAAA/w8MAwzAAwPAgAAA/w8MAwzAAwPAgAADMPD/PAzzwP8PgAADMPD/PAzzwP8PgAAAPw8z8DAwAAPDgAAAPw8z8DAwAAPDgAAD8P/w/Az/88zMgAAD8P/w/Az/88zMgAADM8PM8zAM8A8MgAADM8PM8zAM8A8MgAADD/AAMzzAMzADgAADD/AAMzzAMzADgAAAMwDzzMDDzw/wgAAAMwDzzMDDzw/wgAAAwz8DMw/M/A/MgAAAwz8DMw/M/A/MgAADA8PD/w8A8AMAgAADA8PD/w8A8AMAgAAA/z88//wPMDwPgAAA/z88//wPMDwPgAAA8AAD/Mw88zzAgAAA8AAD/Mw88zzAgAAD////D/M/PP//gAAD////D/M/PP//gAAAAAzMzMzMzMAAgAAAAAzMzMzMzMAAgAAA/8/8Mw8wP8/8gAAA/8/8Mw8wP8/8gAAAwMwDPDD8z8wMgAAAwMwDPDD8z8wMgAAAwM8A/wMPD8wMgAAAwM8A/wMPD8wMgAAAwMww8MwwM8wMgAAAwMww8MwwM8wMgAAA/8zP/wzMD8/8gAAA/8zP/wzMD8/8gAAAAAzMDwMAPMAAgAAAAAzMDwMAPMAAgAAA=";
        String ewm="Qk1+BgAAAAAAAD4AAAAoAAAAZAAAAGQAAAABAAEAAAAAAEAGAAAAAAAAAAAAAAAAAAACAAAAAAAA///////////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAAADPAAAzPPzwD////AAAAAAAzwAAMzz88A////wAAAAP/P8P8/DM//A////8AAAAD/z/D/PwzP/wP////AAAAAwM8D/DzPMM/D////wAAAAMDPA/w8zzDPw////8AAAADAzw888P8Dz/P////AAAAAwM8PPPD/A8/z////wAAAAMDM8/PDzAwAw////8AAAADAzPPzw8wMAMP////AAAAA/8/DMAPAwPwD////wAAAAP/PwzADwMD8A////8AAAAAADDzAPPD8zAP////AAAAAAAw8wDzw/MwD////wAAAA//8DDP8APD88////8AAAAP//Awz/ADw/PP////AAAAD8MAD/Aw/zAAD////wAAAA/DAA/wMP8wAA////8AAAAAw8PwAAAP8MwD////AAAAAMPD8AAAD/DMA////wAAAAAAAzPMDMMA/A////8AAAAAAAMzzAzDAPwP////AAAADPPwPz8MM8zDD////wAAAAzz8D8/DDPMww////8AAAAM8AwP8/P/MzwD////AAAADPAMD/Pz/zM8A////wAAAAPz8MPDwM8M/D////8AAAAD8/DDw8DPDPw/////AAAAD/AMMA88D8zPD////wAAAA/wDDAPPA/Mzw////8AAAAPz8PwzPPADMPP////AAAAD8/D8MzzwAzDz////wAAAA//M8PADM8zPDP///8AAAAP/zPDwAzPMzwz////AAAADPDMMDw8MwzMD////wAAAAzwzDA8PDMMzA////8AAAAPDA/88PM/wMwP////AAAADwwP/PDzP8DMD////wAAAA8w8wwzw/zMw8////8AAAAPMPMMM8P8zMPP////AAAADPMM8P8/MzM8A////wAAAAzzDPD/PzMzPAP///8AAAAD888PwD8P/PwD////AAAAA/PPD8A/D/z8A////wAAAAwwMADM/88PwA////8AAAAMMDAAzP/PD8AP////AAAAAz/Azw/wAMzAD////wAAAAM/wM8P8ADMwA////8AAAAMwA8DPDD/Mzwz////AAAADMAPAzww/zM8M////wAAAAwD8PDAwA8z8D////8AAAAMA/DwwMAPM/A/////AAAADA8Dz8zMAwD/D////wAAAAwPA8/MzAMA/w////8AAAAPwMPM/w8wzMP/////AAAAD8DDzP8PMMzD/////wAAAAPzAADD8/8wAPP///8AAAAD8wAAw/P/MADz////AAAAD//w8//wz8P//////wAAAA//8PP/8M/D//////8AAAAAADMzMzMzMwAD////AAAAAAAzMzMzMzMAA////wAAAAP/MD88w/A/P/P///8AAAAD/zA/PMPwPz/z////AAAAAwMz//zzAM8wM////wAAAAMDM//88wDPMDP///8AAAADAzAAD/MwzzAz////AAAAAwMwAA/zMM8wM////wAAAAMDPDDA/DM/MDP///8AAAADAzwwwPwzPzAz////AAAAA/888/APwDM/8////wAAAAP/PPPwD8AzP/P///8AAAAAADPDM8P8zwAD////AAAAAAAzwzPD/M8AA////wAAAA";
        
        
//        byte[] imageData = ewm.getBytes(SystemConfig.CHARSET);
//        if (imageData != null) {
//            ewmImage = Image.getInstance(imageData);
//        }
//        String a = "1234567890";
//        System.out.println(a.substring(0,3));
//        System.out.println(a.substring(3,5));
//        System.out.println(a.substring(5,7));
//        String a = " 1";
//System.out.println(a.endsWith(" ")?a.replace(" ", ""):"12");
        try {
//            String ewmStr = "iVBORw0KGgoAAAANSUhEUgAAAKIAAACiAQAAAADqiSu2AAAACXBIWXMAAC4jAAAuIwF4pT92AAACN0lEQVRIic2Xsa20MBCEFzkggwYsuQ1nbgka4LgGoCVnbgPJDZiMwLr9Z333pBfe6v0BCbd8J1m2NbOzEFHgQob5d0EaalZfp5C3gCIXn7ferDrqVu9KLy/Fm5Pzq6dFR2nxfF6m9HbxNGFnyW066jbPWHFPNPk6x8zJTjr695tkjjRypd8Fq2g+k9uxG6qPZPZoJ5+VtD4iDVTlJR1Db7uopUeXaEx1keIYAi6ITx2tXapDjyMdY6z4e/C86+jfb/I/+OIV7Mwir1c4UJxsNi2FQEPeE4pcKO/sipKuIlCDl83LumsQravoFnIJ5snw2zHBrr0ddPQWvnhC65clD5XghBA9LkhFaxcZJhl8HSPupYKyjuY9ikkgcY7iN/KidSV1G9lFChqCfVxHp6O38AW6P/rvM0ElBJ2VdzIoKARq52TOhHsxZxQ06CjRR+IQqDvlb/Gbii5vx/I7hwxyiJT0Dr5gtP5LumfX6Nw6uIYiPOx42TEejxYnyINOR1sGxNpBoK2Dz1eddRQ7w0hhH4JcKz77/ZrewhcvyEsGGjTu+s6AoqUeP+hWeEqcnyxziY4GVG1D4jd0c9LS0lIEO9uCg9yZLenoLXyBfgF5TTJeQK/SwZ86iuVs6+BVUkTmkrzrKJbjrZf4eUaxynyxls4YqvCEYYRauKXT0Xv4Qj4VJAlkqsDY/TMbfU1bB495FZ3hYFDqW2ffU7dC6202wnzW5l236ihSBxLHciJ0nO3z3aKhf73Jf0wOhGS5gUs9AAAAAElFTkSuQmCC";
//            String ewmStr = "iVBORw0KGgoAAAANSUhEUgAAAIsAAACLCAIAAADee/2oAAADtklEQVR42u3bUXasIBBFUec/aTOApJdQ91Sp6eNnnvEB2yVQlxyn17OvwyF4h9CRXX8899c/rdy89V+sPKc4KDv9WulyMqoKKaQQK0T1ZKWVoVB4z9Y7hHOuP1AhhRRqEtoa2dqIUPNHbYYLp0x8ZlJIIYVeKhSupMOXYOvXa/MiNT4KKaTQ24WoFTm1Y++rDiikkEKvEKotc/s26rUnh+vmvm3AbVUfhRT6eqHwU+5Pnp7g+ROFvlUIO5hCp9e1UmyYYE2mQatnfRRSSKFMiNpFh4WDvq88vhSmKrCXzVBIIYXmhcJjN+ExoLASQb0EY/ULhRRSCBEKRy383FPnb/A21+4Jay4X+yGFFFKoJBTmOlSyEg4xtbbGyZO3UyGFFGoSCteyYe0yjKjD7vRVIgqDqZBCCo0JUVMU1ROqElGbDgemOoUUUggU6ss/+mDCgcBrJU0Tm0IKKYQIhWXEcKcdSvcF5LeEZAoppFCrEL6ZDz/l1Dw0mUVR5RiFFFIIFKLqgNQ6dbI4S9VkmxI1hRRSCBEK98PhmRhqAU3h4a9pUslVSCGFEKEw6cFTHJwBr2j0BVf1lYJCCin0WeigrzO7wq98eHgoDMjDZqxWfRRSSKFbM9YwQ0L6dnJ/TkwVQwsvikIKKcQKUadbkAAYTKfweYhqT32loJBCCu0IUYVFajuNz3kDcRcOrJBCCnUI4Zt5vNt4M/C4qzcfUkghhWihWxpHDR9VUwiz/OQehRRSCBHCwxLq1A5ejqTiLmrGveypQgophAhRMwo1AYQvwWShk2rhp+copJBCrFDf2RpqQY+XYmvbgDDvX2+PQgopNC9EdTKcovqGL4z5w8MBqwmeQgoptCM0UBSgqgNUJRc/9NP0eimkkEKIEF4MxRnO0oXnQ+G2pDAaCimkECJEZcz4ljskx4sUfbnX6kpBIYUUKglR52bCPKavyhDOQ2EWlbxMCimkUJNQuEoOf4tamuOlBCpVWn/vFVJIIVYI3xiHH25qjY6HQH1VWuA0lkIKKVTKh6jwhup2H1W406AORSmkkEKgEHXV+oakxeX2UEXVsPFATUEhhRTayYf6wt1bIp+wfkFVTwqVXIUUUogVChMaCo/qbS3dr02reH6mkEIKtQpRB1aoU0QDJdS+nUayCVFIIYX+gRBerqXydarckMymCimk0FuE7i19UmvigTlYIYUUmhGi4pxaZB7eTJVQqReuNq0qpJBCHUL4SRrkW5zb95V9Q9fLJyukkEKIkNdjL4Wefv0AeVHfqRlqooMAAAAASUVORK5CYII=";
//            String ewmStr = "iVBORw0KGgoAAAANSUhEUgAAAIsAAACLCAIAAADee/2oAAADsElEQVR42u3aQXbbMBAEUd3/0skym8gUpquHtF1cyhZJ4OMJwDRef7yefb3sgu8h9Mquf7d7/8kn3/rkhv9pw+gR4UO/eI2jh16+j0IKKcQK4e/0SbOP/vTFI45uOBtn4csPelUhhRQqCYW/1/jUMhsxR51+9GLU8Lq8oUIKKfQooaOepdpGrePDYYHvPRRSSKEnz0Nh22Zf3zRbqLAopJBCVSFqddurioZTHfWI8OnbVR+FFPr1Qr186Dd/8j0SPIUU+tFC2MEUKGvBp7GjCXI2kYST1sVZH4UUUigTwrfTC6F1OGXiC3o8OlJIIYVAoTDYDmsK+GmbhWHRy+AVUkihZaGFOWb2404Bh0WB9hJfIYUUKgn1cp3e1j1c+PbmxeTlFVJIIURoIRGhftxnnL3xgQ+v48qpQgop9IEQHgKFC+hblsL4JIq0VCGFFEKEwvmD+nGf7fzx0UAF28iIUUghhRChsG1UCBQ2mzptE8bh7CcKKaQQKxQ2gApmejMBnotTrpWagkIKKTSqnOLpNX4eKLxzqQY6u7NCCimECIU90qtCUjekzgPtjw+FFFIIEeptnqljN5sdSs06swRLIYUUaghRz7tltU29WFhGKb2GQgophAhRwTbe2nBxTN251z/zcwoKKaQQJETVLqml+WZfL7zPcW1bIYUUgoSoPHuhFEsdDAoTbirvV0ghhRpCvV00Hi3jpQ38nBPbLQoppBAihM8WYSM3vz6rRPRmyvl+SCGFFDpZy/USmvCcXy+Dp6jC//l0paCQQgqdCIX5B75jx9fo1JIaP5Z0+YYKKaQQIrSQeeNdjP+JrXjmlWWFFFKoIUTFxtSymyqP4sd3qOYc74cUUkihbMe6EMzcW8SkFv0zGGA/pJBCCtVqCrN8iKpEhDfE24VvMOYJnkIKKXQi1EudqX7EtwELQskmRCGFFEKEZnfv1VKpHsGX3TPFZJgqpJBCrBD1mIXsPOQMyx+94F8hhRSqCvVyHaqwSCU9+zPKTFohhRRChGZxby99wQNyXIgqHFQSPIUUUuh9Xe6WXsOz83CSoAYBkmAppJBCiFCvQ2dds1DRCDcY1PsopJBCy0J43Fs6AZMvjqkwqddAhRRSqCqEzzph4ZU9QJPvItZKGwoppNCjhKjiYzijhGH8bG09C8Au/0chhRR6uNBmnTTs2dnaOoz5FVJIobuEwp9XPBfvrdqp3i8dS1JIIYVYIWqHjBxzyWugm3NMOCu/vb9CCilECHk99lLo6ddf0cE+SxccRBsAAAAASUVORK5CYII=";
            byte bb[] = base64s.decodeBuffer(ewm);
//            File f = new File("f:/aabb.bmp");
//            FileInputStream fil = new FileInputStream(f);
//            byte[] buf = new byte[(int) f.length()];
//            fil.read(buf);
            byte[] imageData = bb;
            FileOutputStream fos = new FileOutputStream(new File("f:/ewm.jpg"));
            fos.write(imageData);
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        FileOutputStream out;
//        try {
//            out = new FileOutputStream(new File("f:/123.jpg"));
//        out.write(base64s.decodeBuffer(ewm));
//        out.flush();
//        out.close();
//        } catch (FileNotFoundException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            
//            String imagePath = "F:/pic/0000/P41002-115154.jpg";
//            String pdfPath = "F:/test.pdf";
//            BufferedImage img = ImageIO.read(new File(imagePath));
//            FileOutputStream fos = new FileOutputStream(pdfPath);
//            Document doc = new Document(null, 0, 0, 0, 0);
//            doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
//            Image image = Image.getInstance(ewm.getBytes());
//            PdfWriter.getInstance(doc, fos);
//            doc.open();
//            doc.add(image);
//            doc.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (BadElementException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
        
        System.out.println(Double.parseDouble("0"));
    }
}
