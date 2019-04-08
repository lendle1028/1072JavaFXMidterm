/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendle.courses.soa.midterm;

/**
 *
 * @author lendle
 */
public class AddressEntry {
    private String address;

    public AddressEntry(String url) {
        this.address = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
