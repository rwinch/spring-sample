package demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
    name = "dummy_table" )
public class DummyEntity
    implements
        Comparable< DummyEntity >
{
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO )
    public long dummy_id;

    public String dummy_name;

    protected DummyEntity ( )
    {
    }

    public DummyEntity (
            final String dummy_name
        )
    {
        super( );
        this.dummy_name = dummy_name;
    }

    @Override
    public String toString ( )
    {
        return "DummyEntity [dummy_id=" + this.dummy_id + ", dummy_name=" + this.dummy_name + "]";
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode ( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) ( dummy_id ^ ( dummy_id >>> 32 ) );
        result = prime * result + ( ( dummy_name == null ) ? 0 : dummy_name.hashCode( ) );
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals (
        final Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass( ) != obj.getClass( ) )
            return false;
        final DummyEntity other = (DummyEntity) obj;
        if ( dummy_id != other.dummy_id )
            return false;
        if ( dummy_name == null )
        {
            if ( other.dummy_name != null )
                return false;
        }
        else if ( !dummy_name.equals( other.dummy_name ) )
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo (
        final DummyEntity other )
    {
        return dummy_name.compareTo( other.dummy_name );
    }

}
