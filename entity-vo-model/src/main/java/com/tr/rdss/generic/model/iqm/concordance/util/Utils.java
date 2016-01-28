package com.tr.rdss.generic.model.iqm.concordance.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.DaoResult;
import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.ModelUtil;
import com.tr.rdss.generic.model.iqm.RDCCheck;
import com.tr.rdss.generic.model.iqm.concordance.EntityVO;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public abstract class Utils {
    
    private static final String permIdDelim = ";";

    /**
     * 
     * @param name attribute name
     * @param value attribute value
     * @return checking result
     * <br>
     * Currently, it only checks whether the input attribute value is null.
     */
    public static boolean validateData(String name, Object value) {
        if (value != null) {
            return true;
        } else {
            Utils.printMessage(name + " == null");
        }
        return false;
    }

    /**
     * 
     * @param name attribute name
     * @param arr attribute list
     * @return checking result
     * <br>
     * Currently, it only checks whether the input attribute list is empty (null)
     */
    public static boolean validateData(String name, List<Attribute> arr) {
        if (arr != null || arr.size() > 0) {
            return true;
        } else {
            Utils.printMessage(name + " == null || " + name + ".size() == 0");
        }
        return false;
    }
    
    /**
     * 
     * @param str string value consisting of a list of Long value (with permIdDelim as delimiters)
     * @return a list of Long value
     */
    public static List<Long> getLongListFromString(String str) {
        List<Long> res = new ArrayList<Long>();
        if (str == null || str.trim().equals("")) {
            return res;
        }
        StringTokenizer token = new StringTokenizer(str, permIdDelim, false);
        while (token.hasMoreTokens()) {
            res.add(Long.parseLong(token.nextToken()));
        }
        return res;
    }

    /**
     * 
     * @param permIds a list of Long values (perm id)
     * @return a string value consisting of the input Long values with permIdDelim as delimiters
     */
    public static String toPermIdsString(List<Long> permIds) {
        if (permIds == null || permIds.size() == 0) {
            return null;
        }
        StringBuffer strBuf = new StringBuffer();
        for (Long permId : permIds) {
            strBuf.append(permId.toString()).append(permIdDelim);
        }
        return strBuf.toString();
    }

    /**
     * 
     * @param showAll a flag to label whether print all the calling trace or just the direct calling method
     * <br>
     * print the calling method info to System.out
     */
    public static void printTrace(Boolean showAll) {
        StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
        if (showAll) {
            for (int i = 2; i < stackElements.length; i++) {
                System.out.println(stackElements[i]);
            }
        } else if(stackElements.length>=3) {
            System.out.println("++++++++++ " + stackElements[2] + " ++++++++++");
        } else if(stackElements.length>=1) {
            System.out.println("++++++++++ " + stackElements[stackElements.length-1] + " ++++++++++");
        }

    }

    /**
     * 
     * @param e Exception
     * <br>
     * print error message to System.err
     */
    public static void printTrace(Exception e) {
        System.err.println("======================== error start ========================");
        printTrace(false);
        System.err.println(e);
        System.err.println("======================== error   end ========================");

    }

    /**
     * 
     * @param msg message
     * <br>
     * print message to System.err
     */ 
    public static void printMessage(String msg) {
        System.err.println(msg);

    }

    /**
     * 
     * @param e Exception
     * @see #printMessage(java.lang.String) 
     */
    public static void printMessage(Exception e) {
        printMessage(e.toString());
    }

    /**
     * 
     * @param c the host object
     * <br>
     * print all the available methods of the input object
     */
    public static void printMethods(Object c) {
        printTrace(false);
        Method[] m = c.getClass().getDeclaredMethods();
        for (int i = 0; i < m.length; i++) {
            Class[] parms = m[i].getParameterTypes();
            List<String> parmList = new ArrayList<String>();
            for (int j = 0; j < parms.length; j++) {
                parmList.add(parms[j].getName());
            }

            System.out.println(m[i].getName() + ":" + parmList + ":" + m[i].getReturnType().getName());
        }
        System.out.println("====== end of printMethods ======");
    }

    /**
     * 
     * @param c class
     * @param prefix prefix for output
     * <br>
     * print all the parents of the input class
     */
    public static void printParents(Class c, String prefix) {
        if (c.getSuperclass() != null && !c.getSuperclass().equals("")) {
            System.out.println("superClass:" + prefix + c.getClass().getSuperclass());
            printParents(c.getSuperclass(), prefix + "  ");
        }
        Class[] classes = c.getInterfaces();
        for (int i = 0; i < classes.length; i++) {
            System.out.println("interface :" + prefix + classes[i]);
            printParents(classes[i], prefix + "  ");
        }
    }

    /**
     * 
     * @param o object
     * @param methodName available method name of the object
     * @param parms input parameters of the method
     * @return response of the method
     * @throws InvalidServiceMethodCallException
     */
    public static Object callMethod(Object o, String methodName, Object[] parms) throws InvalidServiceMethodCallException {

        Class[] parmTypes = null;
        if (parms == null) {
            parmTypes = new Class[]{};
        } else {
            parmTypes = new Class[parms.length];
        }

        for (int i = 0; i < parmTypes.length; i++) {
            if (parms[i] == null) {
                throw new IllegalArgumentException("No parameter should be null: methodName=" + methodName + ",parameter_pos=" + i + " (starting from 0)");
            }
            parmTypes[i] = parms[i].getClass();
        }

        try {
            return o.getClass().getMethod(methodName, parmTypes).invoke(o, parms);
        } catch (NoSuchMethodException | IllegalAccessException e) {
//            printMessage("====== !!! output available methods for check !!! ======");
//            Utils.printMethods(o);
//            printTrace(false);
//            e.printStackTrace();
            throw new InvalidServiceMethodCallException(e);
        } catch (InvocationTargetException e) {
            // in general case, the root cause exception from ADF RMI calls will be
            //  wrapped by EJBException > ServiceException 
            throw new InvalidServiceMethodCallException((Exception)getRootCauseException(e));
        }
    }
    
    private static Throwable getRootCauseException(Throwable e) {
        if (e.getCause()!= null && e.getCause() instanceof Exception )
            return getRootCauseException(e.getCause());
        else 
            return e;
    }

    /**
     * 
     * @param entityVO entityVO
     * @return string value of entityVO in JSON format 
     * @throws JSONException 
     * @see #toJSON(com.tr.rdss.generic.model.iqm.Entity)
     */
    public static String toJSON(EntityVO entityVO) throws JSONException {
        if (entityVO == null) {
            return toJSON((Entity) null);
        } else {
            return toJSON(entityVO.getEntity());
        }
    }

    /**
     * 
     * @param entity entity
     * @return string value of entity in JSON format 
     * @throws JSONException 
     */
    public static String toJSON(Entity entity) throws JSONException {

        String res = null;

        DaoResult daoResult = new DaoResult(true, "create success");
        daoResult.setEntity(entity);
        if (entity != null) {
            daoResult.setKey(entity.getKey());
            daoResult.setKey_value(entity.getKeyValue());
        }
        res = toJSON(daoResult);

        return res;
    }
    
    /**
     * 
     * @param rdcCheck rdcCheck asset
     * @return string value of rdcCheck in JSON format 
     * @throws JSONException 
     */

    public static String toJSON(RDCCheck rdcCheck) throws JSONException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String res = mapper.writeValueAsString(rdcCheck);

            return res;
        } catch (IOException ex) {
            throw new JSONException(ex);
        }
    }

//    /**
//     * 
//     * @param e exception
//     * @return string value of exception in JSON format
//     * @throws Exception 
//     */
//    public static String getFailureJSON(Exception e) throws Exception {
//        try {
//
//            DaoResult daoResult = new DaoResult(false, "failed with errors"
//                + e.toString());
//            return toJSON(daoResult);
//
//        } catch (Exception e1) {
//            throw new JSONException(e1);
////            System.exit(1);
//        }
//    }

    /**
     * 
     * @param daoResult daoResult
     * @return string value of daoResult in JSON format 
     * @throws JSONException 
     */
    public static String toJSON(DaoResult daoResult) throws JSONException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(daoResult);
        } catch (IOException e) {
//            printTrace(e);
            throw new JSONException(e);
        }
    }

    /**
     * 
     * @param objectStr string value of daoResult in JSON format
     * @return constructed DaoResult object
     * @throws JSONException 
     */
    public static DaoResult fromJSON(String objectStr) throws JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        DaoResult res = null;

        try {
            res = objectMapper.readValue(objectStr, DaoResult.class);
        } catch (Exception e) {            
//            printTrace(e);
            throw new JSONException(e);
        }
        return res;
    }

    /**
     * 
     * @param jsonStr string value of daoResult in JSON format
     * @return constructed EntityVO object
     * @throws JSONException 
     */
    public static EntityVO getEntityVOFromJSON(String jsonStr)
        throws JSONException {
        return getEntityVOFromDaoResult(fromJSON(jsonStr));
    }

    /**
     * 
     * @param daoResult daoResult which holds the entity object 
     * @return constructed EntityVO
     */
    public static EntityVO getEntityVOFromDaoResult(DaoResult daoResult) {
        if (daoResult == null) {
            return null;
        } else {
                EntityVO entityVO = EntityVO.createEntityVO(daoResult
                    .getEntity());
                return entityVO;
        }
    }

    
    /**
     * 
     * @param date target date
     * @param from date from
     * @param to date to
     * @return checking result, of whether the target date is within the range [from, to)
     */
    private static boolean checkDateWithinRange(java.util.Date date,
        java.util.Date from, java.util.Date to) {
        return (!(from.after(date)) && date.before(to));
    }

    /**
     * 
     * @param date date
     * @return string format of date
     * @see com.tr.rdss.generic.model.iqm.ModelUtil#formatDateString(java.util.Date) 
     */
    public static String convertToString(java.util.Date date) {
        return ModelUtil.formatDateString(date);
    }

    /**
     * 
     * @param dateStr string format of date
     * @return date
     * @see com.tr.rdss.generic.model.iqm.ModelUtil#formatDate(java.lang.String) 
     */
    public static java.sql.Date convertToDate(String dateStr) {
        return new java.sql.Date(ModelUtil.formatDate(dateStr).getTime());
    }

    /**
     * 
     * @param a attribute a
     * @param b attribute b
     * @return comparing result, whether the two attributes are equal (i.e. same name+value+effective_from+effective_to)

     */
    public static boolean compare(Attribute a, Attribute b) {
//        throws InvalidPropertyException {
        if (a.getName() == null || a.getValue() == null || b.getName() == null
            || b.getValue() == null) {
            return false;
        }
        if (a.getName().equals(b.getName())
            && a.getValue().equals(b.getValue())
            && (" " + a.getEffectiveFrom()).equals(" "
                + b.getEffectiveFrom())
            && (" " + a.getEffectiveTo()).equals(" " + b.getEffectiveTo())) {
            return true;
        } else {
            return false;
        }
    }

}
