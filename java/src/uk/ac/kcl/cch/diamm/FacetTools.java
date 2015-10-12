package uk.ac.kcl.cch.diamm;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.type.*;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.FacetCriterionEntity;
import uk.ac.kcl.cch.facet.FacetType;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 26/05/11
 * Time: 12:24
 * To change this template use File | Settings | File Templates.
 */
public class FacetTools {

    /**
     * Retrieve cached criteria in db
     * NOTE:  REQUIRES OPEN TRANSACTION!!
     *
     * @param type
     * @return
     */
    public static ArrayList<FacetCriterion> getCachedFacetCriteria(FacetType type) {
        ArrayList<FacetCriterion> crits = new ArrayList<FacetCriterion>();
        /*try {
            HibernateUtil.beginTransaction();*/
        int typeKey = getFacetTypeKey(type.getTypeName());
        List<FacetCriterionEntity> entity = HibernateUtil.getSession().createCriteria(FacetCriterionEntity.class).add(Restrictions.eq("facettype", typeKey)).addOrder(Order.asc("orderno")).list();
        for (int i = 0; i < entity.size(); i++) {
            FacetCriterionEntity fce = entity.get(i);
            crits.add(toCriterion(fce));
        }
        /* } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
        }*/
        return crits;
    }

    public static boolean criteriaCached(int key){
        String fullHql = "SELECT count(*) from FacetCriterionEntity as fce"
         + " WHERE fce.facettype=" + key;
        /*if (constraintKeys != null && constraintKeys.size() > 0) {
            fullHql += " AND " + DIAMMFacetManager.getMasterKeyName() + " IN (" + constraintKeys.toString() + ")";
        } */
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        Long count = (Long) query.uniqueResult();
        if (count>0){
            return true;
        }
        return false;
    }

    public static boolean criteriaCached(FacetType type){
        int typeKey = getFacetTypeKey(type.getTypeName());
        return criteriaCached(typeKey);
    }


    public static FacetCriterion getCachedFacetCriteria(FacetType type, int ckey) {
        try{
            int typeKey = getFacetTypeKey(type.getTypeName());
            FacetCriterionEntity fc=(FacetCriterionEntity) HibernateUtil.getSession().createCriteria(FacetCriterionEntity.class)
                    .add(Restrictions.eq("facettype", typeKey))
                    .add(Restrictions.eq("ckey", ckey))
                    .uniqueResult();
            if (fc!=null){
                return toCriterion(fc);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertCachedFacetCriterion(FacetCriterion crit){

    }

    public static FacetCriterion getCritByKey(int key,List<FacetCriterion> values){
        if (values!=null){
            for (int i = 0; i < values.size(); i++) {
                FacetCriterion fCrit =  values.get(i);
                if (fCrit.getKey()==key){
                    return fCrit;
                }
            }
        }
        return null;
    }

    public static void insertCachedFacetCriteria(List<FacetCriterion> crtieria) {

        //To preserve order
        int x = 1;

        try {

            for (int i = 0; i < crtieria.size(); i++) {
                FacetCriterion fc = crtieria.get(i);
                if (fc.getKeys().size() > 0) {
                    FacetCriterionEntity fce = new FacetCriterionEntity();
                    fce.setCkey(fc.getKey());
                    fce.setLabel(fc.getLabel());
                    fce.setFacettype(getFacetTypeKey(fc.getFacetType().getTypeName()));
                    fce.setCount(fc.getKeys().size());
                    fce.setKeyString(DIAMMFacetManager.serializeKeys(fc.getKeys()));
                    fce.setOrderno(x);
                    HibernateUtil.getSession().save(fce);
                    x += 1;
                } else {
                    fc.getKeys().size();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }


    }




    public static FacetCriterion toCriterion(FacetCriterionEntity fce) {
        ArrayList<Integer> keyArray = new ArrayList<Integer>();
        String[] ks = fce.getKeyString().split(",");
        if (ks != null && ks.length > 0) {
            for (int i = 0; i < ks.length; i++) {
                String k = ks[i];
                try {
                    if (k.length() > 0) {
                        keyArray.add(Integer.parseInt(k));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        }
        return new FacetCriterion(keyArray, getFacetTypeByKey(fce.getFacettype()), fce.getCkey(), fce.getLabel());
    }

    public static FacetType getFacetTypeByKey(int ft) {
        switch (ft) {
            case 1:
                return new ComposerFacetType();
            case 2:
                return new CenturyFacetType();
            case 3:
                return new ClefFacetType();
            case 4:
                return new GenreFacetType();
            case 5:
                return new ItemFacetType();
            case 6:
                return new LanguageFacetType();
            case 7:
                return new NotationFacetType();
            case 8:
                return new PersonFacetType();
            case 9:
                return new ProvenanceFacetType();
            case 10:
                return new SetFacetType();
            case 11:
                return new SourceFacetType();
            case 12:
                return new TextFacetType();
            case 13:
                return new CityFacetType();
            case 14:
                return new ArchiveFacetType();
            case 15:
                return new CountryFacetType();
        }
        return null;

    }


    public static int getFacetTypeKey(String ft) {
        if (ft.equalsIgnoreCase("COMPOSERFACET")) {
            return 1;
        } else if (ft.equalsIgnoreCase("CENTURYFACET")) {
            return 2;
        } else if (ft.equalsIgnoreCase("CLEFFACET")) {
            return 3;
        } else if (ft.equalsIgnoreCase("GENREFACET")) {
            return 4;
        } else if (ft.equalsIgnoreCase("ITEMFACET")) {
            return 5;
        } else if (ft.equalsIgnoreCase("LANGUAGEFACET")) {
            return 6;
        } else if (ft.equalsIgnoreCase("NOTATIONFACET")) {
            return 7;
        } else if (ft.equalsIgnoreCase("PERSONFACET")) {
            return 8;
        } else if (ft.equalsIgnoreCase("PROVENANCEFACET")) {
            return 9;
        } else if (ft.equalsIgnoreCase("SETFACET")) {
            return 10;
        } else if (ft.equalsIgnoreCase("SOURCEFACET")) {
            return 11;
        } else if (ft.equalsIgnoreCase("TEXTFACET")) {
            return 12;
        } else if (ft.equalsIgnoreCase("CITYFACET")) {
            return 13;
        } else if (ft.equalsIgnoreCase("ARCHIVEFACET")) {
            return 14;
        } else if (ft.equalsIgnoreCase("COUNTRYFACET")) {
            return 15;
        }
        return 0;
    }
}
