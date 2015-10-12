package uk.ac.kcl.cch.diamm.model;

import org.hibernate.Query;
import uk.ac.kcl.cch.diamm.dao.CollectionDAO;
import uk.ac.kcl.cch.diamm.dao.CollectionDTO;
import uk.ac.kcl.cch.diamm.dao.CollectionDTO.LinkType;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.DiammUserDAO;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionAction {
    private final String MAP_KEY_LINK = "link";
    private final String MAP_KEY_EDIT = "edit";
    private final String ERR_MSG_LINK_ALREADY_ADDED = "the %object has already been added to this collection";
    private final String ERR_MSG_DUPLICATE_TITLE = "already exists";
    private final String ERR_MSG_NO_CHANGES_MADE = "no changes were made";
    private final String SUC_MSG_LINK_ADDED = "the %object has been successfully added to this collection";
    private final String SUC_MSG_COLLECTION_UPDATED = "details have been successfully updated";
    private final String SUC_MSG_COLLECTION_CREATED = "the collection has been successfully created";

    private Map<String, String> errorMessages;
    private Map<String, String> successMessages;
    private DAOFactory factory = DAOFactory.getFactory();
    private CollectionDAO collectionDao = factory.getCollectionDAO();
    private DiammUserDAO diammUserDao = factory.getDiammUserDAO();

    public void link(CollectionDTO collectionDetails) {
        successMessages = new HashMap<String, String>();
        errorMessages = new HashMap<String, String>();
        Collection collection = collectionDao.findByPrimaryKey(collectionDetails.getCollIdToLink());

        if (collectionDetails.getLinkType().equals(LinkType.SOURCE.getEnumType())) {
            List<Source> sourceList = collection.getSourceList();
            Source source = factory.getSourceDAO().findByPrimaryKey(collectionDetails.getLinkId());

            if (!sourceList.contains(source)) {
                sourceList.add(source);
                successMessages.put(MAP_KEY_LINK, SUC_MSG_LINK_ADDED.replace("%object", String.valueOf(LinkType.SOURCE.getEnumType())));
            } else {
                errorMessages.put(MAP_KEY_LINK, ERR_MSG_LINK_ALREADY_ADDED.replace("%object", String.valueOf(LinkType.SOURCE.getEnumType())));
            }
        } else if (collectionDetails.getLinkType().equals(LinkType.ITEM.getEnumType())) {
            List<Item> itemList = collection.getItemList();
            Item item = factory.getItemDAO().findByPrimaryKey(collectionDetails.getLinkId());

            if (!itemList.contains(item)) {
                itemList.add(item);
                successMessages.put(MAP_KEY_LINK, SUC_MSG_LINK_ADDED.replace("%object", String.valueOf(LinkType.ITEM.getEnumType())));
            } else {
                errorMessages.put(MAP_KEY_LINK, ERR_MSG_LINK_ALREADY_ADDED.replace("%object", String.valueOf(LinkType.ITEM.getEnumType())));
            }
        } else if (collectionDetails.getLinkType().equals(LinkType.IMAGE.getEnumType())) {
            List<Image> imageList = collection.getImageList();
            Image image = factory.getImageDAO().findByPrimaryKey(collectionDetails.getLinkId());

            if (!imageList.contains(image)) {
                imageList.add(image);
                successMessages.put(MAP_KEY_LINK, SUC_MSG_LINK_ADDED.replace("%object", String.valueOf(LinkType.IMAGE.getEnumType())));
            } else {
                errorMessages.put(MAP_KEY_LINK, ERR_MSG_LINK_ALREADY_ADDED.replace("%object", String.valueOf(LinkType.IMAGE.getEnumType())));
            }
        }
        collectionDao.save(collection);
    }

    public Collection create(CollectionDTO collectionDetails) {
        Collection collection = new Collection();
        collection.setTitle(collectionDetails.getCreatedCollTitle());
        collection.setDescription(collectionDetails.getCreatedCollDescription());
        collection.setDiammUser(diammUserDao.findByUsername(collectionDetails.getUsername()));
        successMessages = new HashMap<String, String>();
        errorMessages = collection.getErrorMessages();

        if (errorMessages.isEmpty()) {
            if (!collectionDao.find(collectionDetails.getUsername(), collection.getTitle()).isEmpty()) {
                errorMessages.put(Collection.MAP_KEY_TITLE, ERR_MSG_DUPLICATE_TITLE);
            }
        }

        if (errorMessages.isEmpty()) {
            collection = collectionDao.save(collection);
            successMessages.put(MAP_KEY_LINK, SUC_MSG_COLLECTION_CREATED);
        }

        return collection;
    }

    public Collection find(Integer collectionId) {
        Collection collection = collectionDao.findByPrimaryKey(collectionId);
        return collection;
    }

    public void saveChanges(CollectionDTO collectionDetails) {
        Collection collection = collectionDao.findByPrimaryKey(collectionDetails.getOrigCollId());
        collectionDetails.setOrigCollTitle(collection.getTitle());
        collectionDetails.setOrigCollDescription(collection.getDescription());
        errorMessages = new HashMap<String, String>();
        successMessages = new HashMap<String, String>();

        if (!collectionDetails.hasChanged()) {
            errorMessages.put(MAP_KEY_EDIT, ERR_MSG_NO_CHANGES_MADE);
        }

        if (errorMessages.isEmpty()) {
            Collection anotherCollection = new Collection();
            anotherCollection.setTitle(collectionDetails.getEditedCollTitle());
            anotherCollection.setDescription(collectionDetails.getEditedCollDescription());
            errorMessages = anotherCollection.getErrorMessages();
        }

        if (errorMessages.isEmpty()) {
            if (!collectionDao.find(collectionDetails.getUsername(), collectionDetails.getEditedCollTitle(), collectionDetails.getOrigCollId()).isEmpty()) {
                errorMessages.put(Collection.MAP_KEY_TITLE, ERR_MSG_DUPLICATE_TITLE);
            }
        }

        if (errorMessages.isEmpty()) {
            collection.setTitle(collectionDetails.getEditedCollTitle());
            collection.setDescription(collectionDetails.getEditedCollDescription());
            collectionDao.save(collection);
            successMessages.put(MAP_KEY_EDIT, SUC_MSG_COLLECTION_UPDATED);
        }
    }

    public void delete(CollectionDTO collectionDetails) {
        Collection collection = collectionDao.findByPrimaryKey(collectionDetails.getOrigCollId());
        collectionDao.delete(collection);
    }

    public void unlink(CollectionDTO collectionDetails) {
        Collection collection = collectionDao.findByPrimaryKey(collectionDetails.getOrigCollId());

        if (collectionDetails.getUnlinkType().equals(LinkType.SOURCE.getEnumType())) {
            List<Source> sourceList = collection.getSourceList();
            Source source = factory.getSourceDAO().findByPrimaryKey(collectionDetails.getUnlinkId());
            sourceList.remove(source);
        } else if (collectionDetails.getUnlinkType().equals(LinkType.ITEM.getEnumType())) {
            List<Item> itemList = collection.getItemList();
            Item item = factory.getItemDAO().findByPrimaryKey(collectionDetails.getUnlinkId());

            itemList.remove(item);
        } else if (collectionDetails.getUnlinkType().equals(LinkType.IMAGE.getEnumType())) {
            List<Image> imageList = collection.getImageList();
            Image image = factory.getImageDAO().findByPrimaryKey(collectionDetails.getUnlinkId());
            imageList.remove(image);
        }
        collectionDao.save(collection);
    }

    public List<Collection> getCollectionList(String username) {
        DiammUser diammUser = diammUserDao.findByUsername(username);
        List<Collection> collectionList = diammUser.getCollectionList();
        return collectionList;
    }

    public Map<String, String> getMessages() {
        Map<String, String> messages = null;

        if (errorMessages != null && !errorMessages.isEmpty()) {
            messages = errorMessages;
        } else if (successMessages != null && !successMessages.isEmpty()) {
            messages = successMessages;
        }

        return messages;
    }

    public Boolean hasError() {
        return (errorMessages != null && !errorMessages.isEmpty());
    }

    /**
     * Collection(s) owned by this user that are attached to this image
     *
     * @param imageKey
     * @param username
     * @return all attached collections
     */
    public ArrayList<Collection> getCollectionsAttachedToImage(int imageKey,String username) {
        DiammUser diammUser = diammUserDao.findByUsername(username);
        String hql = "select col from Collection as col "
                +" join col.diammUser as user"
                + " join col.imageList as image"
                +" WHERE user.id="+diammUser.getId()+" AND image.imagekey="+imageKey;
        Query query = HibernateUtil.getSession().createQuery(hql);
        ArrayList<Collection> attached=(ArrayList<Collection>) query.list();
        return attached;
    }

    public static ArrayList<Source> getCollectionsAttachedToSource(int sourceKey) {
        return null;
    }

    public static ArrayList<Item> getCollectionsAttachedToItem(int sourceKey) {
        return null;
    }
}
