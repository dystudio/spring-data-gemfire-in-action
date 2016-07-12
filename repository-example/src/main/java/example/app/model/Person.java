package example.app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * The Person class is an abstract data type modeling a person.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see org.springframework.data.annotation.Id
 * @see org.springframework.data.gemfire.mapping.Region
 * @since 1.0.0
 */
@Region("People")
@SuppressWarnings("unused")
public class Person implements Serializable {

	private static final long serialVersionUID = -7204456214709927355L;

	@Id
	private Long id;

	private LocalDate birthDate;

	private Gender gender;

	private String firstName;
	private String lastName;

	public static Person newPerson(String firstName, String lastName) {
		Assert.hasText(firstName, "firstName is required");
		Assert.hasText(lastName, "lastName is required");

		Person person = new Person();

		person.setFirstName(firstName);
		person.setLastName(lastName);

		return person;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Gender getGender() {
		return gender;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return String.format("%1$s %2$s", getFirstName(), getLastName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Person)) {
			return false;
		}

		Person that = (Person) obj;

		return ObjectUtils.nullSafeEquals(this.getBirthDate(), that.getBirthDate())
			&& ObjectUtils.nullSafeEquals(this.getFirstName(), that.getFirstName())
			&& ObjectUtils.nullSafeEquals(this.getLastName(), that.getLastName());
	}

	@Override
	public int hashCode() {
		int hashValue = 17;
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getBirthDate());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getFirstName());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getLastName());
		return hashValue;
	}

	@Override
	public String toString() {
		return getName();
		//return String.format("{ @type = %1$s, firstName = %2$s, lastName = %3$s, birthDate = %4$s, gender = %5$s }",
		//	getClass().getName(), getFirstName(), getLastName(), toString(getBirthDate()), getGender());
	}

	protected String toString(LocalDate date) {
		return (date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null);
	}

	public Person as(Gender gender) {
		setGender(gender);
		return this;
	}

	public Person born(LocalDate birthDate) {
		setBirthDate(birthDate);
		return this;
	}

	public Person with(Long id) {
		setId(id);
		return this;
	}
}
