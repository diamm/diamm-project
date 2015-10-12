package uk.ac.kcl.cch.diamm;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.DiammUserDAO;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.DiammUser;
import uk.ac.kcl.cch.diamm.model.Image;
import uk.ac.kcl.cch.diamm.model.Item;
import uk.ac.kcl.cch.diamm.servlet.ImageProxy;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * A container class to hold image retrieval functions used by by the facet manager and source detail page
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 09-Nov-2010
 * Time: 13:58:54
 * To change this template use File | Settings | File Templates.
 */
public class ImageSearch {
    public static ArrayList<Image> getImagesByItemKey(int itemKey) {
        String fullHql = "SELECT image from Image as image " +
                " join image.itemimagesByImagekey as itemimage" +
                " WHERE image.availwebsite='Y' and itemimage.itemKey=" + itemKey +
                " order by image.orderno";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        ArrayList<Image> images = (ArrayList<Image>) query.list();
        /*List<Itemimage> ii = HibernateUtil.getSession().createCriteria(Itemimage.class).add(Property.forName("itemKey").eq(itemKey)).list();
        ArrayList<Image> images = new ArrayList<Image>();
        for (int i = 0; i < ii.size(); i++) {
            Itemimage itemimage = ii.get(i);
            Image I = itemimage.getImageByImagekey();
            images.add(I);
        }*/
        return images;
    }

    public static void verifyImages() {

        try {
            ArrayList<Image> images = (ArrayList<Image>) HibernateUtil.getSession().createCriteria(Image.class).list();
            for (int i = 0; i < images.size(); i++) {
                Image image = images.get(i);
                if (!ImageProxy.testURL("http://images.cch.kcl.ac.uk/diamm/liv/thumbnails/" + image.getFilename() + ".jpg")) {
                    image.setAvailwebsite("N");
                } else {
                    image.setAvailwebsite("Y");
                }
                HibernateUtil.getSession().save(image);
            }
        } catch (HibernateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalArgumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
                   /*.add(Restrictions.gt("itemkey",6221)).add(Restrictions.lt("itemkey",71386))*/
    public static void updateItemHasImages() {
        try {
            ArrayList<Item> items = (ArrayList<Item>) HibernateUtil.getSession().createCriteria(Item.class)
                    .createCriteria("itemimagesByItemkey").createCriteria("imageByImagekey").add(Restrictions.eq("availwebsite", "Y"))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                //int num = HibernateUtil.getSession().createSQLQuery("update Item set hasImages=1 where itemKey in (select ItemImage.itemKey from ItemImage,Image where Image.imageKey=ItemImage.imageKey and Image.availWebsite='Y' group by ItemImage.itemKey)").executeUpdate();
                //This is incredibly stupid, but Hibernate update is not working for some reason
                if(item.getHasImages()==0){
                    HibernateUtil.getSession().createSQLQuery("update Item set hasImages=1 where itemKey="+item.getItemkey()).executeUpdate();
                }
                /*if (item.getItemkey()==6221){
                    int stop=0;
                }
                item.setHasImages(1);
                HibernateUtil.getSession().saveOrUpdate(item);*/
            }
        } catch (HibernateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            //HibernateUtil.getSession().flush();
            //HibernateUtil.commitTransaction();
        }
    }

    public static ArrayList<Image> getImagesBySourceKey(int sourceKey) {
        if (sourceKey > 0) {                                                                                                                      //
            return (ArrayList<Image>) HibernateUtil.getSession().createCriteria(Image.class)
                    /*
                    .createCriteria("itemimagesByImagekey").createCriteria("itemByItemkey").add(Restrictions.eq("sourcekey",sourceKey))*/
                    .addOrder(Order.asc("orderno"))
                    .add(Property.forName("availwebsite").like("%Y"))
                    .add(Property.forName("sourcekey").eq(sourceKey))
                    .list();


        }
        return null;
    }

    public static ArrayList<Image> getCommentedImagesByUser(HttpServletRequest request) {
        String uname = request.getRemoteUser();
        if (uname != null) {
            DiammUserDAO userDao = DAOFactory.getFactory().getDiammUserDAO();
            DiammUser user = userDao.findByUsername(uname);
            if (user!=null) {                                                                                                                      //
                return (ArrayList<Image>) HibernateUtil.getSession().createCriteria(Image.class)
                        /*
                        .createCriteria("itemimagesByImagekey").createCriteria("itemByItemkey").add(Restrictions.eq("sourcekey",sourceKey))*/
                        .addOrder(Order.asc("orderno"))
                        .add(Property.forName("availwebsite").eq("Y"))
                        .createCriteria("noteList").add(Restrictions.eq("user",user))
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .list();


            }
        }
        return null;
    }


}
