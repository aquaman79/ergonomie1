package main.java.com.ubo.tp.message.datamodel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import main.java.com.ubo.tp.message.common.Constants;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;

/**
 * Classe du modèle représentant un message.
 *
 * @author S.Lucas
 */
public class Message {
	/**
	 * Identifiant unique du message.
	 */
	protected final UUID mUuid;

	/**
	 * Utilisateur source du message.
	 */
	protected final User mSender;

	/**
	 * Date d'émission du message.
	 */
	protected final long mEmissionDate;

	/**
	 * Corps du message.
	 */
	protected final String mText;

	/**
	 * Liste des tags représentant un utilisateur présent dans le message.
	 */
	protected final Set<String> mUserTags;

	/**
	 * Liste des tags présent dans le message.
	 */
	protected final Set<String> mTags;

	/**
	 * Constructeur.
	 *
	 * @param sender utilisateur à l'origine du message.
	 * @param text   , corps du message.
	 */
	public Message(User sender, String text) {
		this(UUID.randomUUID(), sender, System.currentTimeMillis(), text);
	}

	/**
	 * Constructeur.
	 *
	 * @param messageUuid  , identifiant du message.
	 * @param sender       , utilisateur à l'origine du message.
	 * @param emissionDate , date d'émission du message.
	 * @param text         , corps du message.
	 */
	public Message(UUID messageUuid, User sender, long emissionDate, String text) {
		mUuid = messageUuid;
		mSender = sender;
		mEmissionDate = emissionDate;
		mText = text;
		mTags = new HashSet<>();
		mUserTags = new HashSet<>();

		// Initialisation des mots-clés
		this.initTags(new String(mText));
	}

	/**
	 * Initialisation de la liste de tags présents dans le corps du message.
	 */
	protected void initTags(String text) {
		if (text != null) {
			// Ajoute les tags correspondants aux utilisateurs.
			mUserTags.addAll(this.extractTags(text, Constants.USER_TAG_DELIMITER));

			// Ajoute les tags correspondants aux mots-cl�s.
			mTags.addAll(this.extractTags(text, Constants.WORD_TAG_DELIMITER));
		}
	}

	/**
	 * Retourne les tags présents dans le texte en fonction du caractère de
	 * détection.
	 *
	 * @param text         , Texte à analyser.
	 * @param tagDelimiter , Caractère de délimitation des tags à rechercher.
	 */
	protected Set<String> extractTags(String text, String tagDelimiter) {
		Set<String> tags = new HashSet<>();

		// Ajout d'un caractère spécial pour reconnaitre les éléments
		// réellement
		// taggé
		String specialChar = "~";
		String replacedText = text.replace(tagDelimiter, tagDelimiter + specialChar);

		// Découpage en foncion du délimiteur.
		String[] taggedStrings = replacedText.split(tagDelimiter);

		// Parcours de tous les groupes récupérés
		for (String taggedString : taggedStrings) {
			// Si la chaine courante commencait bien par le délimiteur
			if (taggedString.startsWith(specialChar)) {
				// Récupération du tag (du délimiteur jusqu'au premier
				// espace)
				String newTag = taggedString.split(" ")[0];

				// Suppression du caractère sp�cial
				newTag = newTag.substring(1, newTag.length());

				// Ajout du tag à la liste
				tags.add(newTag);
			}
		}

		return tags;
	}

	/**
	 * @return l'identifiant du message.
	 */
	public UUID getUuid() {
		return mUuid;
	}

	/**
	 * @return l'utilisateur source du message.
	 */
	public User getSender() {
		return mSender;
	}

	/**
	 * @return le corps du message.
	 */
	public String getText() {
		return mText;
	}

	/**
	 * Retourne la date d'émission.
	 */
	public long getEmissionDate() {
		return this.mEmissionDate;
	}

	/**
	 * Retourne une liste clonée des tags du message. <br/>
	 * <i> Les tags sont les mots du message précédés par la
	 * {@link Constants#WORD_TAG_DELIMITER}</i>
	 */
	public Set<String> getTags() {
		return new HashSet<>(mTags);
	}

	/**
	 * Retourne une liste clonée des tags du message représentant un utilisateur.
	 * <br/>
	 * <i> Les tags utilisateurs sont les mots du message précédés par la
	 * {@link Constants#USER_TAG_DELIMITER}</i>
	 */
	public Set<String> getUserTags() {
		return new HashSet<>(mUserTags);
	}

	/**
	 * Indique si le message possède le tag donné.
	 *
	 * @param aTag , tag à rechercher.
	 */
	public boolean containsTag(String aTag) {
		return this.getTags().contains(aTag);
	}

	/**
	 * Indique si le message possède le tag utilisateur donné.
	 *
	 * @param anUserTag , tag utilisateur à rechercher.
	 */
	public boolean containsUserTag(String anUserTag) {
		return this.getUserTags().contains(anUserTag);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int hashCode = 0;

		if (this.mUuid != null) {
			hashCode = this.mUuid.hashCode();
		}

		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		boolean equals = false;

		if (other != null) {
			if (other instanceof Message) {
				Message othermessage = (Message) other;
				equals = (this.getUuid().equals(othermessage.getUuid()));
			}
		}

		return equals;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("[");
		sb.append(this.getClass().getName());
		sb.append("] : ");
		sb.append(this.getUuid());
		sb.append(" {");
		sb.append(this.getText());
		sb.append("}");

		return sb.toString();
	}


}
